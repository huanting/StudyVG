# TouchVG for Windows

This is a lightweight 2D vector drawing framework using [vgcore](https://github.com/rhcad/vgcore) for Windows.

- [TestText project](src/TestText) uses MFC and GDI+.
- [TestView project](src/TestView) uses WIN32 API and GDI+.

If you want to use TouchVG in WPF projects, please visit [vgwpf](https://github.com/rhcad/vgwpf).

## License

This is an open source [LGPL 2.1](LICENSE.md) licensed project. It uses the following open source projects:

- [vgcore](https://github.com/rhcad/vgcore) (LGPL): Cross-platform vector drawing libraries using C++.

## How to Contribute

Contributors and sponsors are welcome. You may translate, commit issues or pull requests on this Github site.
To contribute, please follow the branching model outlined here: [A successful Git branching model](http://nvie.com/posts/a-successful-git-branching-model/).

## Contributors

- [Zhang Yungui](https://github.com/rhcad)
- [Archer](https://github.com/a7ch3r)
- [Pengjun](https://github.com/pengjun) / Line and triangle commands

## How to Compile

- Download or clone [vgcore](https://github.com/rhcad/vgcore) to `..\vgcore`.
- Open `Test_vc9.sln` in Visual Studio 2008 or `Test_vc10.sln` in Visual Studio 2010 (Need VC++), then run `TestView` or `TestCanvas` application.
