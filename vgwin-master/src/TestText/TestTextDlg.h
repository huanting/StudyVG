
// TestTextDlg.h : 头文件
//

#pragma once
#include "afxcmn.h"

class MfcGraphWnd;

// CTestTextDlg 对话框
class CTestTextDlg : public CDialogEx
{
// 构造
public:
	CTestTextDlg(CWnd* pParent = NULL);	// 标准构造函数
    virtual ~CTestTextDlg();

// 对话框数据
	enum { IDD = IDD_TESTTEXT_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV 支持


// 实现
protected:
	HICON m_hIcon;
    MfcGraphWnd *m_view;
    CSliderCtrl m_fontSizeCtrl;
	CSliderCtrl m_strokeWidthCtrl;

	// 生成的消息映射函数
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
    afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
    afx_msg void OnClear();
	DECLARE_MESSAGE_MAP()
};
