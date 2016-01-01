
// TestTextDlg.h : ͷ�ļ�
//

#pragma once
#include "afxcmn.h"

class MfcGraphWnd;

// CTestTextDlg �Ի���
class CTestTextDlg : public CDialogEx
{
// ����
public:
	CTestTextDlg(CWnd* pParent = NULL);	// ��׼���캯��
    virtual ~CTestTextDlg();

// �Ի�������
	enum { IDD = IDD_TESTTEXT_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV ֧��


// ʵ��
protected:
	HICON m_hIcon;
    MfcGraphWnd *m_view;
    CSliderCtrl m_fontSizeCtrl;
	CSliderCtrl m_strokeWidthCtrl;

	// ���ɵ���Ϣӳ�亯��
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
    afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
    afx_msg void OnClear();
	DECLARE_MESSAGE_MAP()
};
