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

package com.orsoncharts.legend;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.util.List;
import java.io.Serializable;
import com.orsoncharts.plot.Plot3D;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.PiePlot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.style.ChartStyle;
import com.orsoncharts.table.ContainerElement;
import com.orsoncharts.table.FlowElement;
import com.orsoncharts.table.GridElement;
import com.orsoncharts.table.HAlign;
import com.orsoncharts.table.ShapeElement;
import com.orsoncharts.table.TableElement;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.table.VAlign;
import com.orsoncharts.table.VerticalFlowElement;
import com.orsoncharts.util.Anchor2D;
import com.orsoncharts.util.ArgChecks;
import com.orsoncharts.util.ObjectUtils;
import com.orsoncharts.util.Orientation;

/**
 * The standard legend builder, which creates a simple legend
 * with a flow layout and optional header and footer text.
 * <br><br>
 * NOTE: This class is serializable, but the serialization format is subject 
 * to change in future releases and should not be relied upon for persisting 
 * instances of this class.
 */
public final class StandardLegendBuilder implements LegendBuilder, 
        Serializable {

    /** An optional header/title for the legend (can be <code>null</code>). */
    private String header;
    
    /** The header alignment (never <code>null</code>). */
    private HAlign headerAlignment;
    
    /** An optional footer for the legend (can be <code>null</code>). */
    private String footer;
    
    /** The footer alignment (never <code>null</code>). */
    private HAlign footerAlignment;

    /** 
     * The row alignment (if <code>null</code>, the row alignment will be
     * derived from the anchor point).
     */
    private HAlign rowAlignment;
    
    /**
     * The column alignment (if <code>null</code>, the column alignment will
     * be derived from the anchor point).
     */
    private VAlign columnAlignment;
    
    /**
     * Creates a builder for a simple legend with no header and no footer.
     */
    public StandardLegendBuilder() {
        this(null, null);
    }
    
    /**
     * Creates a builder for a simple legend with the specified header and/or
     * footer.
     * 
     * @param header  the legend header (<code>null</code> permitted).
     * @param footer  the legend footer (<code>null</code> permitted).
     */
    public StandardLegendBuilder(String header, String footer) {
        this.header = header;
        this.headerAlignment = HAlign.LEFT;
        this.footer = footer;
        this.footerAlignment = HAlign.RIGHT;
        this.rowAlignment = null;
        this.columnAlignment = null;
    }
    
    /**
     * Returns the header text.
     * 
     * @return The header text (possibly <code>null</code>).
     */
    public String getHeader() {
        return this.header;
    }
    
    /**
     * Sets the header text.
     * 
     * @param header  the header (<code>null</code> permitted). 
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Returns the header alignment.
     * 
     * @return The header alignment (never <code>null</code>).
     */
    public HAlign getHeaderAlignment() {
        return this.headerAlignment;
    }
    
    /**
     * Sets the header alignment.
     * 
     * @param align  the header alignment (<code>null</code> not permitted). 
     */
    public void setHeaderAlignment(HAlign align) {
        ArgChecks.nullNotPermitted(align, "align");
        this.headerAlignment = align;
    }
    
    /**
     * Returns the footer text.
     * 
     * @return The footer text (possibly <code>null</code>).
     */
    public String getFooter() {
        return this.footer;
    }
    
    /**
     * Sets the footer text.
     * 
     * @param footer  the footer (<code>null</code> permitted). 
     */
    public void setFooter(String footer) {
        this.footer = footer;
    }
    
    /**
     * Returns the footer alignment.
     * 
     * @return The footer alignment (never <code>null</code>).
     */
    public HAlign getFooterAlignment() {
        return this.footerAlignment;
    }
    
    /**
     * Sets the footer alignment.
     * 
     * @param align  the footer alignment (<code>null</code> not permitted). 
     */
    public void setFooterAlignment(HAlign align) {
        ArgChecks.nullNotPermitted(align, "align");
        this.footerAlignment = align;
    }
    
    /**
     * Returns the row alignment.  The default value is <code>null</code> 
     * which means that the row alignment is derived from the anchor point 
     * (left aligned for anchors on the left side, center alignment for 
     * anchors in the middle, and right aligned for anchors on the right side).
     * 
     * @return The row alignment (possibly <code>null</code>). 
     * 
     * @since 1.1
     */
    public HAlign getRowAlignment() {
        return this.rowAlignment;
    }
    
    /**
     * Sets the row alignment (to override the default alignment that is
     * derived from the legend anchor point).  In most circumstances you 
     * should be able to rely on the default behaviour, so leave this
     * attribute set to <code>null</code>.
     * 
     * @param alignment  the row alignment (<code>null</code> permitted).
     * 
     * @since 1.1
     */
    public void setRowAlignment(HAlign alignment) {
        this.rowAlignment = alignment;    
    }
    
    /**
     * Returns the column alignment.  The default value is <code>null</code> 
     * which means that the column alignment is derived from the anchor point 
     * (top aligned for anchors at the top, center alignment for 
     * anchors in the middle, and bottom aligned for anchors at the bottom).
     * 
     * @return The column alignment (possibly <code>null</code>). 
     * 
     * @since 1.1
     */
    public VAlign getColumnAlignment() {
        return this.columnAlignment;
    }
    
    /**
     * Sets the column alignment (to override the default alignment that is
     * derived from the legend anchor point).  In most circumstances you 
     * should be able to rely on the default behaviour, so leave this
     * attribute set to <code>null</code>.
     * 
     * @param alignment  the column alignment (<code>null</code> permitted).
     * 
     * @since 1.1
     */
    public void setColumnAlignment(VAlign alignment) {
        this.columnAlignment = alignment;
    }
    
    /**
     * Creates and returns a legend (instance of {@link TableElement}) that
     * provides a visual key for the data series in the specified plot.  The
     * plot can be any of the built-in plot types: {@link PiePlot3D}, 
     * {@link CategoryPlot3D} or {@link XYZPlot}.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * @param anchor  the anchor (<code>null</code> not permitted).
     * @param orientation  the orientation (<code>null</code> not permitted).
     * @param style  the chart style (<code>null</code> not permitted).
     * 
     * @return The legend.
     * 
     * @since 1.2
     */
    @Override
    public TableElement createLegend(Plot3D plot, Anchor2D anchor, 
            Orientation orientation, ChartStyle style) {
        
        TableElement legend = createSimpleLegend(plot.getLegendInfo(), anchor,
                orientation, style);
        if (this.header != null || this.footer != null) {
            GridElement compositeLegend = new GridElement();
            if (header != null) {
                TextElement he = new TextElement(this.header, 
                        style.getLegendHeaderFont());
                he.setHorizontalAligment(this.headerAlignment);
                he.setBackgroundColor(style.getLegendHeaderBackgroundColor());
                compositeLegend.setElement(he, "R0", "C1");                
            }
            compositeLegend.setElement(legend, "R1", "C1");
            if (this.footer != null) {
                TextElement fe = new TextElement(this.footer, 
                        style.getLegendFooterFont());
                fe.setHorizontalAligment(this.footerAlignment);
                fe.setBackgroundColor(style.getLegendFooterBackgroundColor());
                compositeLegend.setElement(fe, "R2", "C1");
            }
            return compositeLegend;
        } else {
            return legend;
        }
    }
    
    /**
     * Creates a simple legend based on a horizontal flow layout of the 
     * individual legend items.
     * 
     * @param plot  the plot (<code>null</code> not permitted).
     * @param anchor  the anchor point (<code>null</code> not permitted).
     * @param orientation  the orientation (<code>null</code> not permitted).
     * 
     * @return The simple legend.
     */
    private TableElement createSimpleLegend(List<LegendItemInfo> items,
            Anchor2D anchor, Orientation orientation, ChartStyle style) {
        ArgChecks.nullNotPermitted(items, "items");
        ArgChecks.nullNotPermitted(orientation, "orientation");
        ContainerElement legend;
        if (orientation == Orientation.HORIZONTAL) {
            FlowElement fe = new FlowElement(horizontalAlignment(anchor), 2);
            fe.setRefPoint(anchor.getRefPt());
            legend = fe;
        } else {
            VerticalFlowElement vfe = new VerticalFlowElement(
                    verticalAlignment(anchor), 2); 
            vfe.setRefPoint(anchor.getRefPt());
            legend = vfe;        
        }
        for (LegendItemInfo item : items) {
            Shape shape = item.getShape();
            if (shape == null) {
                shape = style.getLegendItemShape();
            }
            legend.addElement(createLegendItem(item.getLabel(), 
                    style.getLegendItemFont(), style.getLegendItemColor(), 
                    shape, item.getColor(), 
                    style.getLegendItemBackgroundColor()));
        }
        return legend;
    }
    
    /**
     * Returns the horizontal alignment that should be used.
     * 
     * @param anchor  the anchor (<code>null</code> not permitted).
     * 
     * @return The horizontal alignment. 
     */
    private HAlign horizontalAlignment(Anchor2D anchor) {
        if (this.rowAlignment != null) {
            return this.rowAlignment;
        }
        if (anchor.getRefPt().isLeft()) {
            return HAlign.LEFT;
        }
        if (anchor.getRefPt().isRight()) {
            return HAlign.RIGHT;
        }
        return HAlign.CENTER;
    }
        
    /**
     * Returns the vertical alignment that should be used.
     * 
     * @param anchor  the anchor (<code>null</code> not permitted).
     * 
     * @return The vertical alignment. 
     */
    private VAlign verticalAlignment(Anchor2D anchor) {
        if (this.columnAlignment != null) {
            return this.columnAlignment;
        }
        if (anchor.getRefPt().isTop()) {
            return VAlign.TOP;
        }
        if (anchor.getRefPt().isBottom()) {
            return VAlign.BOTTOM;
        }
        return VAlign.MIDDLE;
    }
        
    /**
     * Creates a single item in the legend (normally this represents one
     * data series from the dataset).
     * 
     * @param text  the legend item text (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> not permitted).
     * @param textcolor  the text color (<code>null</code> not permitted).
     * @param shape  the shape (<code>null</code> not permitted).
     * @param shapeColor  the shape color (<code>null</code> not permitted).
     * @param background  the background color (<code>null</code> not 
     *     permitted).
     * 
     * @return A legend item (never <code>null</code>). 
     */
    private TableElement createLegendItem(String text, Font font, 
            Color textColor, Shape shape, Color shapeColor, Color background) {
        // defer argument checks...
        ShapeElement se = new ShapeElement(shape, shapeColor);
        se.setBackgroundColor(background);
        TextElement te = new TextElement(text, font);
        te.setColor(textColor);
        te.setBackgroundColor(background);
        GridElement ge = new GridElement();
        ge.setElement(se, "R1", "C1");
        ge.setElement(te, "R1", "C2");
        return ge;
    }
    
    /**
     * Tests this legend builder for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardLegendBuilder)) {
            return false;
        }
        StandardLegendBuilder that = (StandardLegendBuilder) obj;
        if (!ObjectUtils.equals(this.header, that.header)) {
            return false;
        }
        if (this.headerAlignment != that.headerAlignment) {
            return false;
        }     
        if (!ObjectUtils.equals(this.footer, that.footer)) {
            return false;
        }
        if (this.footerAlignment != that.footerAlignment) {
            return false;
        }
        return true;
    }

}
