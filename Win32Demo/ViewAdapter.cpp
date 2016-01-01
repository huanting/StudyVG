#define WIN32_LEAN_AND_MEAN

#include <windows.h>
#include "ViewAdapter.h"
#include <gimousehelper.h>
#include <mglog.h>
#include <record/recordshapes.h>

ViewAdapter::ViewAdapter() : _hwnd(NULL)
{
    _coreView = GiCoreView::createView(this);
    _helper = new GiMouseHelper(this, _coreView);
}

ViewAdapter::~ViewAdapter()
{
    delete _helper;
    _coreView->destoryView(this);
    _coreView->release();
}

void ViewAdapter::regenAll(bool changed)
{
    if (changed)
        _coreView->submitBackDoc(this, changed);
    _coreView->submitDynamicShapes(this);

    _canvas.clearCachedBitmap();
    InvalidateRect(_hwnd, NULL, FALSE);
}

void ViewAdapter::regenAppend(int sid, long playh)
{
    regenAll(true);
}

void ViewAdapter::redraw(bool)
{
    _coreView->submitDynamicShapes(this);

    InvalidateRect(_hwnd, NULL, FALSE);
}

void ViewAdapter::onSize(int w, int h, int dpi)
{
    _coreView->setScreenDpi(dpi);
    _coreView->onSize(this, w, h);
    _canvas.clearCachedBitmap(true);
}

void ViewAdapter::onDraw(HDC hdc)
{
    onSize(0, 0, GetDeviceCaps(hdc, LOGPIXELSY));
    _coreView->setBkColor(this, GetBkColor(hdc) | 0xFF000000);

    if (_canvas.beginPaint(_hwnd, hdc))             // 开始在画布上绘制
    {
        if (!_canvas.drawCachedBitmap())
        {                                           // 显示上次保存的内容
            _canvas.clearWindow();                  // 使用背景色清除显示
//            _coreView->drawAll(this, &_canvas);     // 显示所有图形
            _canvas.saveCachedBitmap();             // 缓存显示的内容
        }
//        _coreView->dynDraw(this, &_canvas);         // 绘制动态图形
        mgvector<long> shapes;
        _coreView->acquireDynamicShapesArray(shapes);
        mgvector<long> docs;
        _coreView->acquireFrontDocs(docs);

        //_coreView->drawAll(docs, _coreView->acquireGraphics(this), &_canvas);
        _coreView->dynDraw(shapes, _coreView->acquireGraphics(this), &_canvas);

        _canvas.endPaint();                         // 结束绘制
        // record
        if (_coreView->isRecording())
        {
            _coreView->recordShapes(false, tick(), _coreView->getChangeCount(), _coreView->acquireFrontDoc(),
                                    _coreView->acquireDynamicShapes());
        }
    }
}

void ViewAdapter::drawTo(GiCanvas *canvas)
{
    _coreView->drawAll(this, canvas);               // 显示所有图形
    _coreView->dynDraw(this, canvas);               // 绘制动态图形
}

bool ViewAdapter::onLButtonDown(int x, int y, WPARAM wparam)
{
    ::SetCapture(_hwnd);
    return _helper->onLButtonDown((float) x, (float) y, (wparam & MK_CONTROL) != 0, (wparam & MK_SHIFT) != 0);
}

bool ViewAdapter::onLButtonUp(int x, int y)
{
    ::ReleaseCapture();
    return _helper->onMouseUp((float) x, (float) y);
}

bool ViewAdapter::onMouseMove(int x, int y, WPARAM wparam)
{
    return _helper->onMouseMove((float) x, (float) y, (wparam & MK_LBUTTON) != 0, (wparam & MK_RBUTTON) != 0);
}

bool ViewAdapter::onRButtonDown(int x, int y)
{
    return _helper->onRButtonDown((float) x, (float) y);
}

bool ViewAdapter::onRButtonUp(int x, int y)
{
    return _helper->onMouseUp((float) x, (float) y);
}

bool ViewAdapter::setCommand(const char *name)
{
    return _coreView->setCommand(name);
}

void NextPartThreadFunction(void *param)
{
    GiCoreView* _coreView = (GiCoreView*)param;
    _coreView->nextPart();
}

void AnimationThreadFunction(void *param)
{
    GiCoreView* _coreView = (GiCoreView*)param;
    _coreView->clear();
    _coreView->playAll(-100);
    _coreView->stopPlay(false);
}

void RepeatThreadFunction(void *param)
{
    GiCoreView* _coreView = (GiCoreView*)param;
    _coreView->repeatPart();
}

void PrevPartThreadFunction(void *param)
{
    GiCoreView* _coreView = (GiCoreView*)param;
    _coreView->prevPart();
}

bool ViewAdapter::onKeyDown(WPARAM keyCode)
{
    switch (keyCode)
    {
        case VK_ADD:
        {
            zoom(8.f);
        }
            return true;
        case VK_SUBTRACT:
        {
            zoom(-8.f);
        }
            return true;
        case VK_F1:
        {
            long doc = _coreView->acquireFrontDoc();
            if (!doc)
            {
                _coreView->submitBackDoc(NULL, false);
                doc = _coreView->acquireFrontDoc();
            }
            _coreView->startRecord("E:\\TouchVG\\record", doc, false, tick());
        }
            return true;
        case VK_F2:
        {
            _coreView->stopRecord(false);
        }
            return true;
        case VK_F3:
        {
//            GiCorePlay* giCorePlay = new GiCorePlay(_coreView->toHandle());
            bool ret = _coreView->startPlay("E:\\TouchVG\\record", tick(), true);
//            _coreView->nextPart();
            LOGD("startPlay = %d", ret);
        }
            return true;
        case VK_F4:
        {
            //_beginthread(AnimationThreadFunction, 0, _coreView);
        }
            return true;
        case VK_F5:
        {
            //_beginthread(RepeatThreadFunction, 0, _coreView);
        }
            return true;
        case VK_NEXT:
        {
            bool ret = _coreView->nextFrame();
//            _beginthread(NextPartThreadFunction, 0, _coreView);
//            bool ret = _coreView->nextPart();
//            LOGD("nextFrame : %d  isLast=%d isFirst=%d", ret, _coreView->isLast(false), _coreView->isFirst(false));
        }
            return true;
        case VK_PRIOR:
        {
//            bool ret = _coreView->undo(this);
//            LOGD("redo : %d", ret);
            //_beginthread(PrevPartThreadFunction, 0, _coreView);
        }
            return true;
            //开始录
        case VK_LEFT:
        {
			//_coreView->loadFromFile("E:\\TouchVG\\record_new\\test.json");
			_coreView->recordPart(false);
            return true;
        }
        case VK_SHIFT:
        {
            LOGD("eraser activated");
            _coreView->getContext(true).setLineColor(255, 255, 255, 255);
            _coreView->setContext(3);
            return true;
        }
		
        case VK_RIGHT:
        {
            onSize(1920, 1080);
            GiTransform* backup = _coreView->getXform();
            _coreView->zoomToExtent(0.f);
            GiGdipCanvas    fileCanvas;
            fileCanvas.beginPaintBuffered(1920, 1080, RGB(255, 255, 255));
            _coreView->drawAll(this, &fileCanvas);
            fileCanvas.save(L"E:\\TouchVG\\1.jpg", 100);
            _coreView->setXform(backup);
            delete backup;
            return true;
        }
        case VK_F9:
        {
            _coreView->addImageShape("test.jpg", 1, 2, 3, 4, 5);
            _coreView->saveToFile(_coreView->acquireFrontDoc(), "e:test.vg", true);
        }
            return true;

    }
    return false;
}

void ViewAdapter::zoom(float delta)
{
    RECT rc;
    GetWindowRect(this->_hwnd, &rc);
    this->_coreView->twoFingersMove(this, kGiGestureBegan, rc.left, rc.top, rc.right, rc.top, false);
    this->_coreView->twoFingersMove(this, kGiGestureMoved, rc.left - delta, rc.top - delta, rc.right + delta, rc.top + delta, false);
}

void ViewAdapter::zoomChanged()
{
//    LOGD("zoomChanged");
}

void ViewAdapter::dynamicChanged()
{
//    LOGD("dynamicChanged");
}

void ViewAdapter::shapeChanged(MgShape* shape)
{
//    LOGD("shapeChanged");
}

void ViewAdapter::contentChanged()
{
}

long ViewAdapter::tick()
{
    return GetTickCount();
}