/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package rhcad.touchvg.core;

public class mgnear {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected mgnear(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(mgnear obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        touchvgJNI.delete_mgnear(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public static float nearestOnBezier(Point2d pt, Point2d pts, Point2d nearpt) {
    return touchvgJNI.mgnear_nearestOnBezier(Point2d.getCPtr(pt), pt, Point2d.getCPtr(pts), pts, Point2d.getCPtr(nearpt), nearpt);
  }

  public static Box2d bezierBox1(Point2d points) {
    return new Box2d(touchvgJNI.mgnear_bezierBox1(Point2d.getCPtr(points), points), true);
  }

  public static Box2d bezierBox4(Point2d p1, Point2d p2, Point2d p3, Point2d p4) {
    return new Box2d(touchvgJNI.mgnear_bezierBox4(Point2d.getCPtr(p1), p1, Point2d.getCPtr(p2), p2, Point2d.getCPtr(p3), p3, Point2d.getCPtr(p4), p4), true);
  }

  public static void beziersBox(Box2d box, int count, Point2d points, boolean closed) {
    touchvgJNI.mgnear_beziersBox__SWIG_0(Box2d.getCPtr(box), box, count, Point2d.getCPtr(points), points, closed);
  }

  public static void beziersBox(Box2d box, int count, Point2d points) {
    touchvgJNI.mgnear_beziersBox__SWIG_1(Box2d.getCPtr(box), box, count, Point2d.getCPtr(points), points);
  }

  public static boolean beziersIntersectBox(Box2d box, int count, Point2d points, boolean closed) {
    return touchvgJNI.mgnear_beziersIntersectBox__SWIG_0(Box2d.getCPtr(box), box, count, Point2d.getCPtr(points), points, closed);
  }

  public static boolean beziersIntersectBox(Box2d box, int count, Point2d points) {
    return touchvgJNI.mgnear_beziersIntersectBox__SWIG_1(Box2d.getCPtr(box), box, count, Point2d.getCPtr(points), points);
  }

  public static void cubicSplinesBox(Box2d box, int n, Point2d knots, Vector2d knotvs, boolean closed, boolean hermite) {
    touchvgJNI.mgnear_cubicSplinesBox__SWIG_0(Box2d.getCPtr(box), box, n, Point2d.getCPtr(knots), knots, Vector2d.getCPtr(knotvs), knotvs, closed, hermite);
  }

  public static void cubicSplinesBox(Box2d box, int n, Point2d knots, Vector2d knotvs, boolean closed) {
    touchvgJNI.mgnear_cubicSplinesBox__SWIG_1(Box2d.getCPtr(box), box, n, Point2d.getCPtr(knots), knots, Vector2d.getCPtr(knotvs), knotvs, closed);
  }

  public static void cubicSplinesBox(Box2d box, int n, Point2d knots, Vector2d knotvs) {
    touchvgJNI.mgnear_cubicSplinesBox__SWIG_2(Box2d.getCPtr(box), box, n, Point2d.getCPtr(knots), knots, Vector2d.getCPtr(knotvs), knotvs);
  }

  public static boolean cubicSplinesIntersectBox(Box2d box, int n, Point2d knots, Vector2d knotvs, boolean closed, boolean hermite) {
    return touchvgJNI.mgnear_cubicSplinesIntersectBox__SWIG_0(Box2d.getCPtr(box), box, n, Point2d.getCPtr(knots), knots, Vector2d.getCPtr(knotvs), knotvs, closed, hermite);
  }

  public static boolean cubicSplinesIntersectBox(Box2d box, int n, Point2d knots, Vector2d knotvs, boolean closed) {
    return touchvgJNI.mgnear_cubicSplinesIntersectBox__SWIG_1(Box2d.getCPtr(box), box, n, Point2d.getCPtr(knots), knots, Vector2d.getCPtr(knotvs), knotvs, closed);
  }

  public static boolean cubicSplinesIntersectBox(Box2d box, int n, Point2d knots, Vector2d knotvs) {
    return touchvgJNI.mgnear_cubicSplinesIntersectBox__SWIG_2(Box2d.getCPtr(box), box, n, Point2d.getCPtr(knots), knots, Vector2d.getCPtr(knotvs), knotvs);
  }

  public static void getRectHandle(Box2d rect, int index, Point2d pt) {
    touchvgJNI.mgnear_getRectHandle(Box2d.getCPtr(rect), rect, index, Point2d.getCPtr(pt), pt);
  }

  public static void moveRectHandle(Box2d rect, int index, Point2d pt, boolean lockCornerScale) {
    touchvgJNI.mgnear_moveRectHandle__SWIG_0(Box2d.getCPtr(rect), rect, index, Point2d.getCPtr(pt), pt, lockCornerScale);
  }

  public static void moveRectHandle(Box2d rect, int index, Point2d pt) {
    touchvgJNI.mgnear_moveRectHandle__SWIG_1(Box2d.getCPtr(rect), rect, index, Point2d.getCPtr(pt), pt);
  }

  public mgnear() {
    this(touchvgJNI.new_mgnear(), true);
  }

}
