/* ============
 * Orson Charts
 * ============
 * 
 * (C)opyright 2013, by Object Refinery Limited.
 * 
 */

package com.orsoncharts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.awt.Color;
import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;

/**
 * Tests for the {@link Chart3D} class.
 */
public class Chart3DTest {
 
    @Test
    public void checkEquals() {
        Chart3D c1 = Chart3DFactory.createPieChart("title", "subtitle", 
                createPieDataset());
        Chart3D c2 = Chart3DFactory.createPieChart("title", "subtitle",
                createPieDataset());
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(null));
        
        // background painter
        c1.setBackground(new StandardRectanglePainter(Color.RED));
        assertFalse(c1.equals(c2));
        c2.setBackground(new StandardRectanglePainter(Color.RED));
        assertTrue(c1.equals(c2));
        
        // title
        c1.setTitle("ABC");
        assertFalse(c1.equals(c2));
        c2.setTitle("ABC");
        assertTrue(c1.equals(c2));
        
        c1.setTitle((String) null);
        assertFalse(c1.equals(c2));
        c2.setTitle((String) null);
        assertTrue(c1.equals(c2));
        
        // title anchor
        c1.setTitleAnchor(TitleAnchor.BOTTOM_LEFT);
        assertFalse(c1.equals(c2));
        c2.setTitleAnchor(TitleAnchor.BOTTOM_LEFT);
        assertTrue(c1.equals(c2));
        
        // legend builder
        c1.setLegendBuilder(null);
        assertFalse(c1.equals(c2));
        c2.setLegendBuilder(null);
        assertTrue(c1.equals(c2));
        
        // chart box color
        c1.setChartBoxColor(Color.CYAN);
        assertFalse(c1.equals(c2));
        c2.setChartBoxColor(Color.CYAN);
        assertTrue(c1.equals(c2));
    }
    
    /**
     * A check for serialization.
     */
    @Test
    public void testSerializationPieChart() {
        Chart3D c1 = Chart3DFactory.createPieChart("title", "subtitle", 
                createPieDataset());
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
    
    /**
     * A check for serialization of area charts.
     */
    @Test
    public void testSerializationAreaChart() {
        Chart3D c1 = Chart3DFactory.createAreaChart("title", "subtitle",
                createCategoryDataset(), "rowAxis", "columnAxis", "valueAxis");
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }
    
    /**
     * A check for serialization of bar charts.
     */
    @Test
    public void testSerializationBarChart() {
        Chart3D c1 = Chart3DFactory.createBarChart("title", "subtitle",
                createCategoryDataset(), "rowAxis", "columnAxis", "valueAxis");
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }

    /**
     * A check for serialization of line charts.
     */
    @Test
    public void testSerializationLineChart() {
        Chart3D c1 = Chart3DFactory.createLineChart("title", "subtitle",
                createCategoryDataset(), "rowAxis", "columnAxis", "valueAxis");
        Chart3D c2 = (Chart3D) TestUtils.serialized(c1);
        assertEquals(c1, c2);
    }

    private PieDataset3D createPieDataset() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("United States", new Double(30.0));
        dataset.add("France", new Double(20.0));
        return dataset; 
    }
    
    private CategoryDataset3D createCategoryDataset() {
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
        DefaultKeyedValues s1 = new DefaultKeyedValues();
        s1.put("Q1", 1.0);
        s1.put("Q2", 7.0);
        s1.put("Q3", 3.0);
        s1.put("Q4", 4.0);
        dataset.addSeriesAsRow("Acme Widgets Ltd", s1);
        return dataset;
    }
}