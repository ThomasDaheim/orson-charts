/* ===========================================================
 * Orson Charts : a 3D chart library for the Java(tm) platform
 * ===========================================================
 * 
 * (C)opyright 2013-2015, by Object Refinery Limited.  All rights reserved.
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the GPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * Orson Charts home page:
 * 
 * http://www.object-refinery.com/orsoncharts/index.html
 * 
 */

package com.orsoncharts.label;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.orsoncharts.TestUtils;

/**
 * Tests for the {@link StandardXYZLabelGenerator} class.
 */
public class StandardXYZLabelGeneratorTest {
    
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardXYZLabelGenerator lg1 = new StandardXYZLabelGenerator();
        StandardXYZLabelGenerator lg2 = new StandardXYZLabelGenerator();
        assertTrue(lg1.equals(lg2));
        assertFalse(lg1.equals(null));
        
        lg1 = new StandardXYZLabelGenerator("%s TEST");
        assertFalse(lg1.equals(lg2));
        lg2 = new StandardXYZLabelGenerator("%s TEST");
        assertTrue(lg1.equals(lg2));
    }
    
    /**
     * Checks for serialization support.
     */
    @Test
    public void testSerialization() {
        StandardXYZLabelGenerator lg1 = new StandardXYZLabelGenerator();
        StandardXYZLabelGenerator lg2 = new StandardXYZLabelGenerator();
        TestUtils.serialized(lg1);
        assertTrue(lg1.equals(lg2));
    }
}
