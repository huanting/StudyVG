/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package rhcad.touchvg.core;

public class MgCmdCompass extends MgCmdArc3P {
  private transient long swigCPtr;

  protected MgCmdCompass(long cPtr, boolean cMemoryOwn) {
    super(touchvgJNI.MgCmdCompass_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MgCmdCompass obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        touchvgJNI.delete_MgCmdCompass(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public MgCmdCompass(String name) {
    this(touchvgJNI.new_MgCmdCompass__SWIG_0(name), true);
  }

  public MgCmdCompass() {
    this(touchvgJNI.new_MgCmdCompass__SWIG_1(), true);
  }

  public void release() {
    touchvgJNI.MgCmdCompass_release(swigCPtr, this);
  }

  public boolean initialize(MgMotion sender, MgStorage s) {
    return touchvgJNI.MgCmdCompass_initialize(swigCPtr, this, MgMotion.getCPtr(sender), sender, MgStorage.getCPtr(s), s);
  }

  public boolean draw(MgMotion sender, GiGraphics gs) {
    return touchvgJNI.MgCmdCompass_draw(swigCPtr, this, MgMotion.getCPtr(sender), sender, GiGraphics.getCPtr(gs), gs);
  }

  public boolean click(MgMotion sender) {
    return touchvgJNI.MgCmdCompass_click(swigCPtr, this, MgMotion.getCPtr(sender), sender);
  }

}
