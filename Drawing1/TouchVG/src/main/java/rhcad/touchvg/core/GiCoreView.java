/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package rhcad.touchvg.core;

public class GiCoreView extends MgCoreView {
  private transient long swigCPtr;

  protected GiCoreView(long cPtr, boolean cMemoryOwn) {
    super(touchvgJNI.GiCoreView_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(GiCoreView obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        throw new UnsupportedOperationException("C++ destructor does not have public access");
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public static GiCoreView createView(GiView view, int type) {
    long cPtr = touchvgJNI.GiCoreView_createView__SWIG_0(GiView.getCPtr(view), view, type);
    return (cPtr == 0) ? null : new GiCoreView(cPtr, false);
  }

  public static GiCoreView createView(GiView view) {
    long cPtr = touchvgJNI.GiCoreView_createView__SWIG_1(GiView.getCPtr(view), view);
    return (cPtr == 0) ? null : new GiCoreView(cPtr, false);
  }

  public static GiCoreView createMagnifierView(GiView newview, GiCoreView mainView, GiView mainDevView) {
    long cPtr = touchvgJNI.GiCoreView_createMagnifierView(GiView.getCPtr(newview), newview, GiCoreView.getCPtr(mainView), mainView, GiView.getCPtr(mainDevView), mainDevView);
    return (cPtr == 0) ? null : new GiCoreView(cPtr, false);
  }

  public void destoryView(GiView view) {
    touchvgJNI.GiCoreView_destoryView(swigCPtr, this, GiView.getCPtr(view), view);
  }

  public int acquireGraphics(GiView view) {
    return touchvgJNI.GiCoreView_acquireGraphics(swigCPtr, this, GiView.getCPtr(view), view);
  }

  public void releaseGraphics(int gs) {
    touchvgJNI.GiCoreView_releaseGraphics(swigCPtr, this, gs);
  }

  public int acquireFrontDocs(Longs docs) {
    return touchvgJNI.GiCoreView_acquireFrontDocs(swigCPtr, this, Longs.getCPtr(docs), docs);
  }

  public static void releaseDocs(Longs docs) {
    touchvgJNI.GiCoreView_releaseDocs(Longs.getCPtr(docs), docs);
  }

  public int getSkipDrawIds(Ints ids) {
    return touchvgJNI.GiCoreView_getSkipDrawIds(swigCPtr, this, Ints.getCPtr(ids), ids);
  }

  public int acquireDynamicShapesArray(Longs shapes) {
    return touchvgJNI.GiCoreView_acquireDynamicShapesArray(swigCPtr, this, Longs.getCPtr(shapes), shapes);
  }

  public static void releaseShapesArray(Longs shapes) {
    touchvgJNI.GiCoreView_releaseShapesArray(Longs.getCPtr(shapes), shapes);
  }

  public int drawAll(int doc, int gs, GiCanvas canvas) {
    return touchvgJNI.GiCoreView_drawAll__SWIG_0(swigCPtr, this, doc, gs, GiCanvas.getCPtr(canvas), canvas);
  }

  public int drawAll(Longs docs, int gs, GiCanvas canvas) {
    return touchvgJNI.GiCoreView_drawAll__SWIG_1(swigCPtr, this, Longs.getCPtr(docs), docs, gs, GiCanvas.getCPtr(canvas), canvas);
  }

  public int drawAll(Longs docs, int gs, GiCanvas canvas, Ints ignoreIds) {
    return touchvgJNI.GiCoreView_drawAll__SWIG_2(swigCPtr, this, Longs.getCPtr(docs), docs, gs, GiCanvas.getCPtr(canvas), canvas, Ints.getCPtr(ignoreIds), ignoreIds);
  }

  public int drawAppend(int doc, int gs, GiCanvas canvas, int sid) {
    return touchvgJNI.GiCoreView_drawAppend__SWIG_0(swigCPtr, this, doc, gs, GiCanvas.getCPtr(canvas), canvas, sid);
  }

  public int dynDraw(int shapes, int gs, GiCanvas canvas) {
    return touchvgJNI.GiCoreView_dynDraw__SWIG_0(swigCPtr, this, shapes, gs, GiCanvas.getCPtr(canvas), canvas);
  }

  public int dynDraw(Longs shapes, int gs, GiCanvas canvas) {
    return touchvgJNI.GiCoreView_dynDraw__SWIG_1(swigCPtr, this, Longs.getCPtr(shapes), shapes, gs, GiCanvas.getCPtr(canvas), canvas);
  }

  public int drawAll(GiView view, GiCanvas canvas) {
    return touchvgJNI.GiCoreView_drawAll__SWIG_3(swigCPtr, this, GiView.getCPtr(view), view, GiCanvas.getCPtr(canvas), canvas);
  }

  public int drawAppend(GiView view, GiCanvas canvas, int sid) {
    return touchvgJNI.GiCoreView_drawAppend__SWIG_1(swigCPtr, this, GiView.getCPtr(view), view, GiCanvas.getCPtr(canvas), canvas, sid);
  }

  public int dynDraw(GiView view, GiCanvas canvas) {
    return touchvgJNI.GiCoreView_dynDraw__SWIG_2(swigCPtr, this, GiView.getCPtr(view), view, GiCanvas.getCPtr(canvas), canvas);
  }

  public int setBkColor(GiView view, int argb) {
    return touchvgJNI.GiCoreView_setBkColor(swigCPtr, this, GiView.getCPtr(view), view, argb);
  }

  public static void setScreenDpi(int dpi, float factor) {
    touchvgJNI.GiCoreView_setScreenDpi__SWIG_0(dpi, factor);
  }

  public static void setScreenDpi(int dpi) {
    touchvgJNI.GiCoreView_setScreenDpi__SWIG_1(dpi);
  }

  public void onSize(GiView view, int w, int h) {
    touchvgJNI.GiCoreView_onSize(swigCPtr, this, GiView.getCPtr(view), view, w, h);
  }

  public void setViewScaleRange(GiView view, float minScale, float maxScale) {
    touchvgJNI.GiCoreView_setViewScaleRange(swigCPtr, this, GiView.getCPtr(view), view, minScale, maxScale);
  }

  public void setPenWidthRange(GiView view, float minw, float maxw) {
    touchvgJNI.GiCoreView_setPenWidthRange(swigCPtr, this, GiView.getCPtr(view), view, minw, maxw);
  }

  public void setGestureVelocity(GiView view, float vx, float vy) {
    touchvgJNI.GiCoreView_setGestureVelocity(swigCPtr, this, GiView.getCPtr(view), view, vx, vy);
  }

  public boolean onGesture(GiView view, GiGestureType type, GiGestureState state, float x, float y, boolean switchGesture) {
    return touchvgJNI.GiCoreView_onGesture__SWIG_0(swigCPtr, this, GiView.getCPtr(view), view, type.swigValue(), state.swigValue(), x, y, switchGesture);
  }

  public boolean onGesture(GiView view, GiGestureType type, GiGestureState state, float x, float y) {
    return touchvgJNI.GiCoreView_onGesture__SWIG_1(swigCPtr, this, GiView.getCPtr(view), view, type.swigValue(), state.swigValue(), x, y);
  }

  public boolean twoFingersMove(GiView view, GiGestureState state, float x1, float y1, float x2, float y2, boolean switchGesture) {
    return touchvgJNI.GiCoreView_twoFingersMove__SWIG_0(swigCPtr, this, GiView.getCPtr(view), view, state.swigValue(), x1, y1, x2, y2, switchGesture);
  }

  public boolean twoFingersMove(GiView view, GiGestureState state, float x1, float y1, float x2, float y2) {
    return touchvgJNI.GiCoreView_twoFingersMove__SWIG_1(swigCPtr, this, GiView.getCPtr(view), view, state.swigValue(), x1, y1, x2, y2);
  }

  public boolean submitBackDoc(GiView view, boolean changed) {
    return touchvgJNI.GiCoreView_submitBackDoc(swigCPtr, this, GiView.getCPtr(view), view, changed);
  }

  public boolean submitDynamicShapes(GiView view) {
    return touchvgJNI.GiCoreView_submitDynamicShapes(swigCPtr, this, GiView.getCPtr(view), view);
  }

  public float calcPenWidth(GiView view, float lineWidth) {
    return touchvgJNI.GiCoreView_calcPenWidth(swigCPtr, this, GiView.getCPtr(view), view, lineWidth);
  }

  public GiGestureType getGestureType() {
    return GiGestureType.swigToEnum(touchvgJNI.GiCoreView_getGestureType(swigCPtr, this));
  }

  public GiGestureState getGestureState() {
    return GiGestureState.swigToEnum(touchvgJNI.GiCoreView_getGestureState(swigCPtr, this));
  }

  public static int getVersion() {
    return touchvgJNI.GiCoreView_getVersion();
  }

  public boolean isZoomEnabled(GiView view) {
    return touchvgJNI.GiCoreView_isZoomEnabled(swigCPtr, this, GiView.getCPtr(view), view);
  }

  public void setZoomEnabled(GiView view, boolean enabled) {
    touchvgJNI.GiCoreView_setZoomEnabled(swigCPtr, this, GiView.getCPtr(view), view, enabled);
  }

  public int exportSVG(int doc, int gs, String filename) {
    return touchvgJNI.GiCoreView_exportSVG__SWIG_0(swigCPtr, this, doc, gs, filename);
  }

  public int exportSVG(GiView view, String filename) {
    return touchvgJNI.GiCoreView_exportSVG__SWIG_1(swigCPtr, this, GiView.getCPtr(view), view, filename);
  }

  public boolean startRecord(String path, int doc, boolean forUndo, int curTick, MgStringCallback c) {
    return touchvgJNI.GiCoreView_startRecord__SWIG_0(swigCPtr, this, path, doc, forUndo, curTick, MgStringCallback.getCPtr(c), c);
  }

  public boolean startRecord(String path, int doc, boolean forUndo, int curTick) {
    return touchvgJNI.GiCoreView_startRecord__SWIG_1(swigCPtr, this, path, doc, forUndo, curTick);
  }

  public void stopRecord(boolean forUndo) {
    touchvgJNI.GiCoreView_stopRecord(swigCPtr, this, forUndo);
  }

  public boolean recordShapes(boolean forUndo, int tick, int changeCount, int doc, int shapes) {
    return touchvgJNI.GiCoreView_recordShapes__SWIG_0(swigCPtr, this, forUndo, tick, changeCount, doc, shapes);
  }

  public boolean recordShapes(boolean forUndo, int tick, int changeCount, int doc, int shapes, Longs exts, MgStringCallback c) {
    return touchvgJNI.GiCoreView_recordShapes__SWIG_1(swigCPtr, this, forUndo, tick, changeCount, doc, shapes, Longs.getCPtr(exts), exts, MgStringCallback.getCPtr(c), c);
  }

  public boolean recordShapes(boolean forUndo, int tick, int changeCount, int doc, int shapes, Longs exts) {
    return touchvgJNI.GiCoreView_recordShapes__SWIG_2(swigCPtr, this, forUndo, tick, changeCount, doc, shapes, Longs.getCPtr(exts), exts);
  }

  public boolean undo(GiView view) {
    return touchvgJNI.GiCoreView_undo(swigCPtr, this, GiView.getCPtr(view), view);
  }

  public boolean redo(GiView view) {
    return touchvgJNI.GiCoreView_redo(swigCPtr, this, GiView.getCPtr(view), view);
  }

  public boolean onPause(int curTick) {
    return touchvgJNI.GiCoreView_onPause(swigCPtr, this, curTick);
  }

  public boolean onResume(int curTick) {
    return touchvgJNI.GiCoreView_onResume(swigCPtr, this, curTick);
  }

  public boolean restoreRecord(int type, String path, int doc, int changeCount, int index, int count, int tick, int curTick) {
    return touchvgJNI.GiCoreView_restoreRecord(swigCPtr, this, type, path, doc, changeCount, index, count, tick, curTick);
  }

  public void traverseOptions(MgOptionCallback c) {
    touchvgJNI.GiCoreView_traverseOptions(swigCPtr, this, MgOptionCallback.getCPtr(c), c);
  }

  public void setOptionBool(String name, boolean value) {
    touchvgJNI.GiCoreView_setOptionBool(swigCPtr, this, name, value);
  }

  public void setOptionInt(String name, int value) {
    touchvgJNI.GiCoreView_setOptionInt(swigCPtr, this, name, value);
  }

  public void setOptionFloat(String name, float value) {
    touchvgJNI.GiCoreView_setOptionFloat(swigCPtr, this, name, value);
  }

  public void setOptionString(String name, String value) {
    touchvgJNI.GiCoreView_setOptionString(swigCPtr, this, name, value);
  }

  public boolean startPlay(String path, int tick, boolean applyZeroFrame) {
    return touchvgJNI.GiCoreView_startPlay__SWIG_0(swigCPtr, this, path, tick, applyZeroFrame);
  }

  public boolean startPlay(String path, int tick) {
    return touchvgJNI.GiCoreView_startPlay__SWIG_1(swigCPtr, this, path, tick);
  }

  public boolean stopPlay(boolean forUndo) {
    return touchvgJNI.GiCoreView_stopPlay(swigCPtr, this, forUndo);
  }

  public boolean isPlayingPart(boolean forUndo) {
    return touchvgJNI.GiCoreView_isPlayingPart(swigCPtr, this, forUndo);
  }

  public boolean isLast(boolean forUndo) {
    return touchvgJNI.GiCoreView_isLast(swigCPtr, this, forUndo);
  }

  public boolean isFirst(boolean forUndo) {
    return touchvgJNI.GiCoreView_isFirst(swigCPtr, this, forUndo);
  }

  public boolean nextFrame() {
    return touchvgJNI.GiCoreView_nextFrame(swigCPtr, this);
  }

  public boolean recordPart(boolean forUndo) {
    return touchvgJNI.GiCoreView_recordPart(swigCPtr, this, forUndo);
  }

  public boolean nextPart(int speed) {
    return touchvgJNI.GiCoreView_nextPart__SWIG_0(swigCPtr, this, speed);
  }

  public boolean nextPart() {
    return touchvgJNI.GiCoreView_nextPart__SWIG_1(swigCPtr, this);
  }

  public boolean repeatPart() {
    return touchvgJNI.GiCoreView_repeatPart(swigCPtr, this);
  }

  public boolean prevPart(int speed) {
    return touchvgJNI.GiCoreView_prevPart__SWIG_0(swigCPtr, this, speed);
  }

  public boolean prevPart() {
    return touchvgJNI.GiCoreView_prevPart__SWIG_1(swigCPtr, this);
  }

  public boolean playAll(int speed) {
    return touchvgJNI.GiCoreView_playAll__SWIG_0(swigCPtr, this, speed);
  }

  public boolean playAll() {
    return touchvgJNI.GiCoreView_playAll__SWIG_1(swigCPtr, this);
  }

  public GiTransform getXform() {
    long cPtr = touchvgJNI.GiCoreView_getXform(swigCPtr, this);
    return (cPtr == 0) ? null : new GiTransform(cPtr, false);
  }

  public void setXform(GiTransform xform) {
    touchvgJNI.GiCoreView_setXform(swigCPtr, this, GiTransform.getCPtr(xform), xform);
  }

  public final static int kNoCmdType = -1;
  public final static int kTestType = 0;
  public final static int kNormalType = 1;

}
