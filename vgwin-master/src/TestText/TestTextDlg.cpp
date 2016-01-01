
// TestTextDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "TestText.h"
#include "TestTextDlg.h"
#include "afxdialogex.h"

#include "MfcGraphWnd.h"
#include <cmdobserver.h>
#include <mgcoreview.h>
#include <gigraph.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

// CTestTextDlg 对话框

CTestTextDlg::CTestTextDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CTestTextDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
    m_view = new MfcGraphWnd();
}

CTestTextDlg::~CTestTextDlg()
{
    delete m_view;
}

void CTestTextDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_FONT_SIZE_SLIDER, m_fontSizeCtrl);
	DDX_Control(pDX, IDC_STROKE_WIDTH_SLIDER, m_strokeWidthCtrl);
}

BEGIN_MESSAGE_MAP(CTestTextDlg, CDialogEx)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_HSCROLL()
    ON_COMMAND(IDC_BUTTON_CLEAR, OnClear)
END_MESSAGE_MAP()

class TestCmdObserver : public CmdObserverDefault
{
public:
    TestCmdObserver() {}

private:
    virtual void onUnloadCommands(MgCmdManager*) {
        delete this;
    }
    virtual void drawInShapeCommand(const MgMotion* sender, MgCommand* cmd, GiGraphics* gs);
};

static float _fontSize = 3.5f;

// CTestTextDlg 消息处理程序

BOOL CTestTextDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

    SetIcon(m_hIcon, TRUE);
    SetIcon(m_hIcon, FALSE);

    CRect rect;
    GetClientRect(&rect);

    m_view->SubclassDlgItem(IDC_GRAPH_VIEW, this);
    m_view->registerObserver(new TestCmdObserver());
    m_view->setCommand("splines");

	m_fontSizeCtrl.SetRange(4, 100);    // mm*2
	m_strokeWidthCtrl.SetRange(1, 50);  // px

	return TRUE;  // 除非将焦点设置到控件，否则返回 TRUE
}

// 如果向对话框添加最小化按钮，则需要下面的代码
//  来绘制该图标。对于使用文档/视图模型的 MFC 应用程序，
//  这将由框架自动完成。

void CTestTextDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // 用于绘制的设备上下文

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// 使图标在工作区矩形中居中
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// 绘制图标
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialogEx::OnPaint();
	}
}

//当用户拖动最小化窗口时系统调用此函数取得光标显示。
HCURSOR CTestTextDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void CTestTextDlg::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{
	CString str1, str2;
    CSliderCtrl* slider = (CSliderCtrl*)pScrollBar;
    int strokeWidth = m_strokeWidthCtrl.GetPos();

	UpdateData();
    _fontSize = 0.5f * m_fontSizeCtrl.GetPos();
	str1.Format(_T("%.1f mm"), _fontSize);
	str2.Format(_T("%d px"), strokeWidth);
	SetDlgItemText(IDC_FONT_SIZE, str1);
	SetDlgItemText(IDC_STROKE_WIDTH, str2);

    if (slider->GetDlgCtrlID() == IDC_STROKE_WIDTH_SLIDER) {
        GiContext ctx(-(float)strokeWidth);
        m_view->getCoreView()->setContext(ctx, GiContext::kLineWidth, 1);
    }
    else if (slider->GetDlgCtrlID() == IDC_FONT_SIZE_SLIDER) {
        m_view->redraw();
    }

	CDialog::OnHScroll(nSBCode, nPos, pScrollBar);
}

void CTestTextDlg::OnClear()
{
    m_view->setCommand("erasewnd");
}

void TestCmdObserver::drawInShapeCommand(const MgMotion*, MgCommand*, GiGraphics* gs)
{
    gs->drawTextAt(0, "Abc123", Point2d::kOrigin(), _fontSize);
}
