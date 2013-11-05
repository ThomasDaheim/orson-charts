/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.awt.Dimension;
import java.awt.geom.Point2D;

/**
 * A collection of utility methods for 2D geometry.
 */
public class Utils2D {
    
    /**
     * Calculates twice the area of a triangle for points specified in 
     * counter-clockwise order (if the points are specified in clockwise order
     * the result will be negative).
     * 
     * @param a  the first point (<code>null</code> not permitted).
     * @param b  the second point (<code>null</code> not permitted).
     * @param c  the third point (<code>null</code> not permitted).
     * 
     * @return The area x 2.
     */
    public static double area2(Point2D a, Point2D b, Point2D c) {
        double ax = a.getX();
        double ay = a.getY();
        double bx = b.getX();
        double by = b.getY();
        double cx = c.getX();
        double cy = c.getY();
        return (ax - cx) * (by - cy) - (ay - cy) * (bx - cx);
    }
    
    public static Point3D normal(Point3D a, Point3D b, Point3D c) {
        double ax = a.x - c.x;
        double ay = a.y - c.y;
        double az = a.z - c.z;
        double bx = b.x - c.x;
        double by = b.y - c.y;
        double bz = b.z - c.z;
        return new Point3D(ay * bz - az * by, az * bx - ax * bz, 
                ax * by - ay * bx);
    }
    
    public static double cross(Point3D a, Point3D b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;    
    }
    
    public static double angle(Point3D a, Point3D b) {
        double dp = a.x * b.x + a.y * b.y + a.z * b.z;
        double alen = length(a);
        double blen = length(b);
        return Math.acos(dp / (alen * blen));    
    }
    
    /**
     * Returns the length of the vector v.
     * 
     * @param v  the vector (<code>null</code> not permitted).
     * 
     * @return 
     */
    public static double length(Point3D v) {
        return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
    }
    
    /**
     * Returns the point in the center of the four supplied points.
     * 
     * @param pt0  point 0 (<code>null</code> not permitted).
     * @param pt1  point 1 (<code>null</code> not permitted).
     * @param pt2  point 2 (<code>null</code> not permitted).
     * @param pt3  point 3 (<code>null</code> not permitted).
     * 
     * @return  The center point (never <code>null</code>).
     */
    public static Point2D centerPoint(Point2D pt0, Point2D pt1, Point2D pt2, 
            Point2D pt3) {
        float x = (float) ((pt0.getX() + pt1.getX() + pt2.getX() + pt3.getX()) 
                / 4.0);
        float y = (float) ((pt0.getY() + pt1.getY() + pt2.getY() + pt3.getY()) 
                / 4.0);
        return new Point2D.Float(x, y);
    }

    /**
     * Returns the dimensions of the smallest rectangle that could contain
     * the supplied points.
     * 
     * @param pts  an array of points (<code>null</code> not permitted).
     * 
     * @return The dimensions.
     */
    public static Dimension findDimension(Point2D[] pts) {
        double minx = Double.POSITIVE_INFINITY;
        double maxx = Double.NEGATIVE_INFINITY;
        double miny = Double.POSITIVE_INFINITY;
        double maxy = Double.NEGATIVE_INFINITY;
        for (Point2D pt : pts) {
            minx = Math.min(minx, pt.getX());
            maxx = Math.max(maxx, pt.getX());
            miny = Math.min(miny, pt.getY());
            maxy = Math.max(maxy, pt.getY());
        }
        return new Dimension((int) (maxx - minx), (int) (maxy - miny));
    }
}
