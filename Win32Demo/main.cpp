//
// Created by Hankcs on 2015/12/3.
//

#define WIN32_LEAN_AND_MEAN
#ifdef _MSC_VER
    #define WIN_MAIN wWinMain
	#define _TLPSTR LPTSTR
#else
    #define WIN_MAIN WinMain
    #define _TLPSTR LPSTR
#endif

#include <windows.h>

static ATOM MyRegisterClass(HINSTANCE);

static BOOL InitInstance(HINSTANCE, int);

static LRESULT CALLBACK
        WndProc(HWND, UINT, WPARAM, LPARAM);

int APIENTRY WIN_MAIN(HINSTANCE hInstance, HINSTANCE, _TLPSTR, int nCmdShow)
{
    MSG msg;

    MyRegisterClass(hInstance);

    if (!InitInstance(hInstance, nCmdShow))
    {
        return FALSE;
    }

    while (GetMessage(&msg, NULL, 0, 0))
    {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }

    return (int) msg.wParam;
}

ATOM MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEX wcex;

    memset(&wcex, 0, sizeof(wcex));
    wcex.cbSize = sizeof(wcex);

    wcex.style = CS_HREDRAW | CS_VREDRAW | CS_DBLCLKS;
    wcex.lpfnWndProc = WndProc;
    wcex.hInstance = hInstance;
    wcex.hCursor = LoadCursor(NULL, IDC_ARROW);
    wcex.hbrBackground = (HBRUSH) (COLOR_WINDOW + 1);
    wcex.lpszClassName = L"Test";

    return RegisterClassEx(&wcex);
}

BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
    HWND hwnd = CreateWindowEx(WS_EX_CLIENTEDGE, L"Test", L"TestView", WS_OVERLAPPEDWINDOW, CW_USEDEFAULT, 0,
                               CW_USEDEFAULT, 0, NULL, NULL, hInstance, NULL);

    if (hwnd)
    {
        ShowWindow(hwnd, nCmdShow);
        UpdateWindow(hwnd);
    }

    return !!hwnd;
}

#include "ViewAdapter.h"
#include <windowsx.h>

static ViewAdapter *_adapter = NULL;

#include <svgcanvas.h>
#include <mglog.h>

void exportSVG(HWND hwnd)
{
    char filename[MAX_PATH];
    GetModuleFileNameA(NULL, filename, MAX_PATH);
    strcpy(strrchr(filename, '.'), ".svg");

    RECT rc;
    GetWindowRect(hwnd, &rc);

    GiSvgCanvas canvas;

    if (canvas.open(filename, rc.right - rc.left, rc.bottom - rc.top))
    {
        _adapter->drawTo(&canvas);
    }
}

// 主窗口的消息处理函数
LRESULT CALLBACK
WndProc(HWND hwnd, UINT message, WPARAM wparam, LPARAM lparam)
{
    bool handled = false;
    //TCHAR operationDes[] = TEXT("+:放大；-:缩小；F1：开始录制；F2：停止录制；F3：开始播放；F4：播放所有录制；F5：播放下一part;");
    //TCHAR operationDes2[] = TEXT("pgdn:下一part；PgUp：上一part；->：保存part；F9：保存");

    switch (message)
    {
        case WM_CREATE:
            _adapter = new ViewAdapter();
            _adapter->setWnd(hwnd);
            _adapter->setCommand("splines");
            break;
        case WM_DESTROY:            // 退出
            PostQuitMessage(0);
            handled = true;
            delete _adapter;
            _adapter = NULL;
            break;
        case WM_ERASEBKGND:         // 不清除背景，避免闪烁
            handled = true;
            break;
        case

            WM_PAINT:              // 重绘主窗口
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hwnd, &ps);


            SetBkColor(hdc, GetSysColor(COLOR_WINDOW));

            _adapter->onDraw(hdc);
            //TextOut(hdc,0, 0, operationDes, wcslen(operationDes));
            //TextOut(hdc,0, 20, operationDes2, wcslen(operationDes));
            EndPaint(hwnd, &ps);
            handled = true;
        }

            break;
        case WM_SIZE:               // 改变窗口大小
            _adapter->onSize(LOWORD(lparam), HIWORD(lparam));
            handled = true;
            break;
        case

            WM_LBUTTONDBLCLK:      // 双击切换测试图形
        {
            LPCSTR names[] = {"splines", "line", "select", "triangle"};
            static int index = -1;
            index = (index + 1) % (sizeof(names) / sizeof(names[0]));
            handled = _adapter->setCommand(names[index]);
        }

            break;
        case WM_LBUTTONDOWN:        // 鼠标按下
//            LOGD("WM_LBUTTONDOWN");
            handled = _adapter->onLButtonDown(GET_X_LPARAM(lparam), GET_Y_LPARAM(lparam), wparam);
//            LOGD("finish WM_LBUTTONDOWN");
            break;
        case WM_LBUTTONUP:          // 鼠标抬起
//            LOGD("WM_LBUTTONUP");
            handled = _adapter->onLButtonUp(GET_X_LPARAM(lparam), GET_Y_LPARAM(lparam));
//            exportSVG(hwnd);
            break;
        case WM_MOUSEMOVE:          // 鼠标移动
            handled = _adapter->onMouseMove(GET_X_LPARAM(lparam), GET_Y_LPARAM(lparam), wparam);
            break;
        case WM_RBUTTONDOWN:        // 右键按下
            handled = _adapter->onRButtonDown(GET_X_LPARAM(lparam), GET_Y_LPARAM(lparam));
            break;
        case WM_RBUTTONUP:          // 右键抬起
            handled = _adapter->onRButtonUp(GET_X_LPARAM(lparam), GET_Y_LPARAM(lparam));
            break;
        case WM_KEYDOWN:
            handled = _adapter->onKeyDown(wparam);
            break;
    }

    return handled || DefWindowProc(hwnd, message, wparam, lparam);
}
