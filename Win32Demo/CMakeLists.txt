cmake_minimum_required(VERSION 3.2)
project(TouchVGWin32)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -m32")
add_definitions(-DUNICODE -D_UNICODE -DWIN32 -DSTD_LOG)
include_directories(
        ../TouchVg/vgcore/core/include
        ../TouchVg/vgcore/core/include/canvas
        ../TouchVg/vgcore/core/include/cmd
        ../TouchVg/vgcore/core/include/cmdbase
        ../TouchVg/vgcore/core/include/cmdbasic
        ../TouchVg/vgcore/core/include/cmdmgr
        ../TouchVg/vgcore/core/include/cmdobserver
        ../TouchVg/vgcore/core/include/export
        ../TouchVg/vgcore/core/include/geom
        ../TouchVg/vgcore/core/include/graph
        ../TouchVg/vgcore/core/include/gshape
        ../TouchVg/vgcore/core/include/jsonstorage
        ../TouchVg/vgcore/core/include/record
        ../TouchVg/vgcore/core/include/shape
        ../TouchVg/vgcore/core/include/shapedoc
        ../TouchVg/vgcore/core/include/storage
        ../TouchVg/vgcore/core/include/test
        ../TouchVg/vgcore/core/include/view
        include
)
file(GLOB_RECURSE VG_CORE "../TouchVg/vgcore/core/src/*.cpp")
add_executable(TouchVGWin32 ${VG_CORE} main.cpp ViewAdapter.cpp GiGdipCanvas.cpp)
target_link_libraries(TouchVGWin32
        gdiplus
        )