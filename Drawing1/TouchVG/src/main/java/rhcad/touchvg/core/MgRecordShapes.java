/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package rhcad.touchvg.core;

public class MgRecordShapes {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected MgRecordShapes(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MgRecordShapes obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        touchvgJNI.delete_MgRecordShapes(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public MgRecordShapes(String path, MgShapeDoc doc, boolean forUndo, int curTick) {
    this(touchvgJNI.new_MgRecordShapes(path, MgShapeDoc.getCPtr(doc), doc, forUndo, curTick), true);
  }

  public int getCurrentTick(int curTick) {
    return touchvgJNI.MgRecordShapes_getCurrentTick(swigCPtr, this, curTick);
  }

  public boolean isLoading() {
    return touchvgJNI.MgRecordShapes_isLoading(swigCPtr, this);
  }

  public void setLoading(boolean loading) {
    touchvgJNI.MgRecordShapes_setLoading(swigCPtr, this, loading);
  }

  public boolean onResume(int ticks) {
    return touchvgJNI.MgRecordShapes_onResume(swigCPtr, this, ticks);
  }

  public void restore(int index, int count, int tick, int curTick) {
    touchvgJNI.MgRecordShapes_restore(swigCPtr, this, index, count, tick, curTick);
  }

  public void stopRecordIndex() {
    touchvgJNI.MgRecordShapes_stopRecordIndex(swigCPtr, this);
  }

  public boolean isPlaying() {
    return touchvgJNI.MgRecordShapes_isPlaying(swigCPtr, this);
  }

  public int getFileTick() {
    return touchvgJNI.MgRecordShapes_getFileTick(swigCPtr, this);
  }

  public int getFileFlags() {
    return touchvgJNI.MgRecordShapes_getFileFlags(swigCPtr, this);
  }

  public int getFileCount() {
    return touchvgJNI.MgRecordShapes_getFileCount(swigCPtr, this);
  }

  public boolean applyFirstFile(MgShapeFactory factory, MgShapeDoc doc) {
    return touchvgJNI.MgRecordShapes_applyFirstFile__SWIG_0(swigCPtr, this, MgShapeFactory.getCPtr(factory), factory, MgShapeDoc.getCPtr(doc), doc);
  }

  public boolean applyFirstFile(MgShapeFactory factory, MgShapeDoc doc, String filename) {
    return touchvgJNI.MgRecordShapes_applyFirstFile__SWIG_1(swigCPtr, this, MgShapeFactory.getCPtr(factory), factory, MgShapeDoc.getCPtr(doc), doc, filename);
  }

  public boolean skipFirstFile() {
    return touchvgJNI.MgRecordShapes_skipFirstFile(swigCPtr, this);
  }

  public int applyRedoFile(MgShapeFactory f, MgShapeDoc doc, MgShapes dyns, int index) {
    return touchvgJNI.MgRecordShapes_applyRedoFile(swigCPtr, this, MgShapeFactory.getCPtr(f), f, MgShapeDoc.getCPtr(doc), doc, MgShapes.getCPtr(dyns), dyns, index);
  }

  public int applyUndoFile(MgShapeFactory f, MgShapeDoc doc, MgShapes dyns, int index, int curTick) {
    return touchvgJNI.MgRecordShapes_applyUndoFile(swigCPtr, this, MgShapeFactory.getCPtr(f), f, MgShapeDoc.getCPtr(doc), doc, MgShapes.getCPtr(dyns), dyns, index, curTick);
  }

  public boolean recordPart() {
    return touchvgJNI.MgRecordShapes_recordPart(swigCPtr, this);
  }

  public int getPartCnt() {
    return touchvgJNI.MgRecordShapes_getPartCnt(swigCPtr, this);
  }

  public RecordPartData getPartData(int index) {
    long cPtr = touchvgJNI.MgRecordShapes_getPartData(swigCPtr, this, index);
    return (cPtr == 0) ? null : new RecordPartData(cPtr, false);
  }

  public int getNextPartIndex() {
    return touchvgJNI.MgRecordShapes_getNextPartIndex(swigCPtr, this);
  }

  public int getPrePartIndex() {
    return touchvgJNI.MgRecordShapes_getPrePartIndex(swigCPtr, this);
  }

  public int getStepDuration() {
    return touchvgJNI.MgRecordShapes_getStepDuration(swigCPtr, this);
  }

  public boolean load() {
    return touchvgJNI.MgRecordShapes_load(swigCPtr, this);
  }

  public boolean isPlayingPart() {
    return touchvgJNI.MgRecordShapes_isPlayingPart(swigCPtr, this);
  }

  public boolean isLast() {
    return touchvgJNI.MgRecordShapes_isLast(swigCPtr, this);
  }

  public boolean isFirst() {
    return touchvgJNI.MgRecordShapes_isFirst(swigCPtr, this);
  }

  public boolean stopPlay() {
    return touchvgJNI.MgRecordShapes_stopPlay(swigCPtr, this);
  }

  public final static int ADD = 1;
  public final static int EDIT = 2;
  public final static int DEL = 4;
  public final static int DYN = 8;
  public final static int DOC_CHANGED = 1;
  public final static int SHAPE_APPEND = 2;
  public final static int DYN_CHANGED = 4;

  public final static int TYPE_UNDO = 0;
  public final static int TYPE_REC = 1;
  public final static int TYPE_PLAY = 2;

}
