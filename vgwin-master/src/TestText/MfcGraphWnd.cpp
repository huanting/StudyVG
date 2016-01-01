#include "stdafx.h"
#include "MfcGraphWnd.h"
#include <gimousehelper.h>
#include <GiGdipCanvas.h>

class MfcViewAdapter : public GiView
{
public:
    MfcViewAdapter();
    virtual ~MfcViewAdapter();

    void setWnd(CWnd* pWnd) { _wnd = pWnd; }
    GiMouseHelper* helper() { return _helper; }
    GiCoreView* coreView() { return _coreView; }

    void onDraw(CDC* pDC);
    void drawTo(GiCanvas* canvas);

    virtual void regenAll(bool changed);
    virtual void regenAppend(int sid, long playh);
    virtual void redraw(bool changed);
    virtual bool useFinger() { return false; }

private:
    GiMouseHelper*  _helper;
    GiCoreView*     _coreView;
    CWnd*           _wnd;
    GiGdipCanvas    _canvas;
};

// MfcGraphWnd
//

BEGIN_MESSAGE_MAP(MfcGraphWnd, CStatic)
    ON_WM_CREATE()
    ON_WM_PAINT()
    ON_WM_ERASEBKGND()
    ON_WM_SIZE()
    ON_WM_MOUSEMOVE()
    ON_WM_LBUTTONDBLCLK()
    ON_WM_LBUTTONDOWN()
    ON_WM_LBUTTONUP()
    ON_WM_RBUTTONDOWN()
    ON_WM_RBUTTONUP()
END_MESSAGE_MAP()

MfcGraphWnd::MfcGraphWnd()
{
    _adapter = new MfcViewAdapter();
}

MfcGraphWnd::~MfcGraphWnd()
{
    delete _adapter;
}

void MfcGraphWnd::redraw()
{
    _adapter->redraw(true);
}

BOOL MfcGraphWnd::setCommand(const char* name)
{
    return _adapter->coreView()->setCommand(name);
}

BOOL MfcGraphWnd::SubclassDlgItem(UINT nID, CWnd* pParent)
{
    BOOL ret = CStatic::SubclassDlgItem(nID, pParent);
    CRect rect;

    if (ret) {
        _adapter->setWnd(this);
        GetClientRect(&rect);
        _adapter->coreView()->onSize(_adapter, rect.Width(), rect.Height());
    }
    return ret;
}

int MfcGraphWnd::OnCreate(LPCREATESTRUCT cs)
{
    _adapter->setWnd(this);
    _adapter->coreView()->onSize(_adapter, cs->cx, cs->cy);
    return CStatic::OnCreate(cs);
}

BOOL MfcGraphWnd::OnEraseBkgnd(CDC *)
{
    return FALSE;
}

void MfcGraphWnd::OnPaint()
{
    CPaintDC dc(this);
    dc.SetBkColor(GetSysColor(COLOR_WINDOW));
    _adapter->onDraw(&dc);
}

void MfcGraphWnd::OnSize(UINT nType, int cx, int cy)
{
    _adapter->coreView()->onSize(_adapter, cx, cy);
    CStatic::OnSize(nType, cx, cy);
}

void MfcGraphWnd::OnMouseMove(UINT nFlags, CPoint point)
{
    if (!_adapter->helper()->onMouseMove((float)point.x, (float)point.y, 
        !!(nFlags & MK_LBUTTON), !!(nFlags & MK_RBUTTON))) {
            CStatic::OnMouseMove(nFlags, point);
    }
}

void MfcGraphWnd::OnLButtonDblClk(UINT nFlags, CPoint point)
{
    if (!_adapter->helper()->onLButtonDblClk((float)point.x, (float)point.y)) {
        CStatic::OnLButtonDblClk(nFlags, point);
    }
}

void MfcGraphWnd::OnLButtonDown(UINT nFlags, CPoint point)
{
    SetFocus();
    SetCapture();
    if (!_adapter->helper()->onLButtonDown((float)point.x, (float)point.y,
        !!(nFlags & MK_CONTROL), !!(nFlags & MK_SHIFT))) {
            CStatic::OnLButtonDown(nFlags, point);
    }
}

void MfcGraphWnd::OnLButtonUp(UINT nFlags, CPoint point)
{
    ::ReleaseCapture();
    if (!_adapter->helper()->onMouseUp((float)point.x, (float)point.y)) {
        CStatic::OnLButtonUp(nFlags, point);
    }
}

void MfcGraphWnd::OnRButtonDown(UINT nFlags, CPoint point)
{
    SetFocus();
    if (!_adapter->helper()->onRButtonDown((float)point.x, (float)point.y)) {
        CStatic::OnRButtonDown(nFlags, point);
    }
}

void MfcGraphWnd::OnRButtonUp(UINT nFlags, CPoint point)
{
    if (!_adapter->helper()->onMouseUp((float)point.x, (float)point.y)) {
        CStatic::OnRButtonUp(nFlags, point);
    }
}

// MfcViewAdapter
//

MfcViewAdapter::MfcViewAdapter() : _wnd(NULL)
{
    _coreView = GiCoreView::createView(this);
    _helper = new GiMouseHelper(this, _coreView);
}

MfcViewAdapter::~MfcViewAdapter()
{
    delete _helper;
    _coreView->destoryView(this);
    _coreView->release();
}

void MfcViewAdapter::regenAll(bool changed)
{
    if (changed) {
        _coreView->submitBackDoc(this, changed);
    }
    _coreView->submitDynamicShapes(this);

    _canvas.clearCachedBitmap();
    if (_wnd->GetSafeHwnd()) {
        _wnd->InvalidateRect(NULL, FALSE);
    }
}

void MfcViewAdapter::regenAppend(int, long)
{
    regenAll(true);
}

void MfcViewAdapter::redraw(bool)
{
    if (_wnd->GetSafeHwnd()) {
        _coreView->submitDynamicShapes(this);
        _wnd->InvalidateRect(NULL, FALSE);
    }
}

void MfcViewAdapter::onDraw(CDC* pDC)
{
    _coreView->setScreenDpi(pDC->GetDeviceCaps(LOGPIXELSY));
    _coreView->setBkColor(this, pDC->GetBkColor() | 0xFF000000);

    if (_canvas.beginPaint(_wnd->GetSafeHwnd(), pDC->GetSafeHdc())) {
        if (!_canvas.drawCachedBitmap()) {
            _canvas.clearWindow();
            _coreView->drawAll(this, &_canvas);
            // TODO: Fix _canvas.saveCachedBitmap();
        }
        _coreView->dynDraw(this, &_canvas);
        _canvas.endPaint();
    }
}

void MfcViewAdapter::drawTo(GiCanvas* canvas)
{
    _coreView->drawAll(this, canvas);
    _coreView->dynDraw(this, canvas);
}

#include <mgview.h>
#include <cmdsubject.h>

MgView* MfcGraphWnd::getCmdView()
{
    return MgView::fromHandle(_adapter->coreView()->viewAdapterHandle());
}

MgCoreView* MfcGraphWnd::getCoreView()
{
    return _adapter->coreView();
}

void MfcGraphWnd::registerObserver(CmdObserver* observer)
{
    getCmdView()->getCmdSubject()->registerObserver(observer);
}

void MfcGraphWnd::unregisterObserver(CmdObserver* observer)
{
    getCmdView()->getCmdSubject()->unregisterObserver(observer);
}
