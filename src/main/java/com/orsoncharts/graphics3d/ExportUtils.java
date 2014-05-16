/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, 2014, by Object Refinery Limited.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * Redistribution of this source file is prohibited.
 * 
 */

package com.orsoncharts.graphics3d;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.imageio.ImageIO;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ExportFormats;

/**
 * Export utility methods.
 * 
 * @since 1.4
 */
public class ExportUtils {
    
    /**
     * Writes the current content to the specified file in SVG format.  This 
     * will only work when the JFreeSVG library is found on the classpath.
     * Reflection is used to ensure there is no compile-time dependency on
     * JFreeSVG.
     * 
     * @param drawable  the drawable (<code>null</code> not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * @param file  the output file (<code>null</code> not permitted).
     * 
     * @return The rendering info.
     */
    public static RenderingInfo writeAsSVG(Drawable3D drawable, int w, int h, 
            File file) {
        if (!ExportFormats.isJFreeSVGAvailable()) {
            throw new IllegalStateException(
                    "JFreeSVG is not present on the classpath.");
        }
        ArgChecks.nullNotPermitted(drawable, "drawable");
        ArgChecks.nullNotPermitted(file, "file");
        try {
            Class<?> svg2Class = Class.forName(
                    "org.jfree.graphics2d.svg.SVGGraphics2D");
            Constructor<?> c1 = svg2Class.getConstructor(int.class, int.class);
            Graphics2D svg2 = (Graphics2D) c1.newInstance(w, h);
            Rectangle2D drawArea = new Rectangle2D.Double(0, 0, w, h);
            RenderingInfo info = drawable.draw(svg2, drawArea);
            Class<?> svgUtilsClass = Class.forName(
                    "org.jfree.graphics2d.svg.SVGUtils");
            Method m1 = svg2Class.getMethod("getSVGElement", (Class[]) null);
            String element = (String) m1.invoke(svg2, (Object[]) null);
            Method m2 = svgUtilsClass.getMethod("writeToSVG", File.class, 
                    String.class);
            m2.invoke(svgUtilsClass, file, element);
            return info;
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Writes a {@link Drawable3D} to the specified file in PDF format.  This 
     * will only work when the OrsonPDF library is found on the classpath.
     * Reflection is used to ensure there is no compile-time dependency on
     * OrsonPDF.
     * 
     * @param drawable  the drawable (<code>null</code> not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * @param file  the output file (<code>null</code> not permitted).
     * 
     * @return The rendering info.
     */
    public static final RenderingInfo writeAsPDF(Drawable3D drawable, 
            int w, int h, File file) {
        if (!ExportFormats.isOrsonPDFAvailable()) {
            throw new IllegalStateException(
                    "OrsonPDF is not present on the classpath.");
        }
        ArgChecks.nullNotPermitted(drawable, "drawable");
        ArgChecks.nullNotPermitted(file, "file");
        try {
            Class<?> pdfDocClass = Class.forName("com.orsonpdf.PDFDocument");
            Object pdfDoc = pdfDocClass.newInstance();
            Method m = pdfDocClass.getMethod("createPage", Rectangle2D.class);
            Rectangle2D rect = new Rectangle(w, h);
            Object page = m.invoke(pdfDoc, rect);
            Method m2 = page.getClass().getMethod("getGraphics2D");
            Graphics2D g2 = (Graphics2D) m2.invoke(page);
            Rectangle2D drawArea = new Rectangle2D.Double(0, 0, w, h);
            RenderingInfo info = drawable.draw(g2, drawArea);
            Method m3 = pdfDocClass.getMethod("writeToFile", File.class);
            m3.invoke(pdfDoc, file);
            return info;
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Writes the current content to the specified file in PNG format.
     * 
     * @param drawable  the drawable (<code>null</code> not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * @param file  the output file (<code>null</code> not permitted).
     * 
     * @return The rendering info.
     * 
     * @throws FileNotFoundException if the file is not found.
     * @throws IOException if there is an I/O problem.
     */
    public static RenderingInfo writeAsPNG(Drawable3D drawable, int w, int h, 
            File file) throws FileNotFoundException, IOException {
        BufferedImage image = new BufferedImage(w, h, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        RenderingInfo result = drawable.draw(g2, new Rectangle(w, h));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        try {
            ImageIO.write(image, "png", out);
        }
        finally {
            out.close();
        }
        return result;
    }

    /**
     * Writes the current content to the specified file in JPEG format.
     * 
     * @param drawable  the drawable (<code>null</code> not permitted).
     * @param w  the chart width.
     * @param h  the chart height.
     * @param file  the output file (<code>null</code> not permitted).
     * 
     * @return The rendering info.
     * 
     * @throws FileNotFoundException if the file is not found.
     * @throws IOException if there is an I/O problem.
     */
    public static RenderingInfo writeAsJPEG(Drawable3D drawable, int w, int h, 
            File file) throws FileNotFoundException, IOException {
        BufferedImage image = new BufferedImage(w, h, 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        RenderingInfo result = drawable.draw(g2, new Rectangle(w, h));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        try {
            ImageIO.write(image, "jpg", out);
        }
        finally {
            out.close();
        }
        return result;
    }

}