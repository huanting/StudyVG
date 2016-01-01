﻿#ifndef TOUCHVG_MFC_GRAPGVIEW_H
#define TOUCHVG_MFC_GRAPGVIEW_H

class MfcViewAdapter;
struct CmdObserver;
struct MgView;
struct MgCoreView;

class MfcGraphWnd : public CStatic
{
public:
    MfcGraphWnd();
    ~MfcGraphWnd();

    BOOL setCommand(const char* name);
    BOOL SubclassDlgItem(UINT nID, CWnd* pParent);
    void redraw();

    MgView* getCmdView();
    MgCoreView* getCoreView();
    void registerObserver(CmdObserver* observer);
    void unregisterObserver(CmdObserver* observer);

protected:
    afx_msg int OnCreate(LPCREATESTRUCT cs);
    afx_msg void OnPaint();
    afx_msg BOOL OnEraseBkgnd(CDC *pDC);
    afx_msg void OnSize(UINT nType, int cx, int cy);
    afx_msg void OnMouseMove(UINT nFlags, CPoint point);
    afx_msg void OnLButtonDblClk(UINT nFlags, CPoint point);
    afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
    afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
    afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
    afx_msg void OnRButtonUp(UINT nFlags, CPoint point);
    DECLARE_MESSAGE_MAP()

private:
    MfcViewAdapter* _adapter;
};

#endif // TOUCHVG_MFC_GRAPGVIEW_H
