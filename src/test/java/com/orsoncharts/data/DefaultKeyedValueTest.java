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

package com.orsoncharts.data;

import com.orsoncharts.TestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Some checks for the {@link DefaultKeyedValue} class.
 */
public class DefaultKeyedValueTest {
    
    @Test
    public void testEquals() {
        DefaultKeyedValue<Number> kv1 = new DefaultKeyedValue<Number>("K1", 
                Double.valueOf(1.0));
        DefaultKeyedValue<Number> kv2 = new DefaultKeyedValue<Number>("K1", 
                Double.valueOf(1.0));
        assertEquals(kv1, kv2);
        assertFalse(kv1.equals(null));
        
        kv1 = new DefaultKeyedValue<Number>("K2", Double.valueOf(1.0));
        assertFalse(kv1.equals(kv2));
        kv2 = new DefaultKeyedValue<Number>("K2", Double.valueOf(1.0));
        assertTrue(kv1.equals(kv2));
       
        kv1 = new DefaultKeyedValue<Number>("K2", Double.valueOf(2.0));
        assertFalse(kv1.equals(kv2));
        kv2 = new DefaultKeyedValue<Number>("K2", Double.valueOf(2.0));
        assertTrue(kv1.equals(kv2));
        
        kv1 = new DefaultKeyedValue<Number>("K2", null);
        assertFalse(kv1.equals(kv2));
        kv2 = new DefaultKeyedValue<Number>("K2", null);
        assertTrue(kv1.equals(kv2));
    }
    
    @Test
    public void testSerialization() {
        DefaultKeyedValue<Number> kv1 = new DefaultKeyedValue<Number>("K1", 
                1.0);
        DefaultKeyedValue<Number> kv2 = (DefaultKeyedValue<Number>) 
                TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
        
        kv1 = new DefaultKeyedValue<Number>("K1", null);
        kv2 = (DefaultKeyedValue<Number>) TestUtils.serialized(kv1);
        assertTrue(kv1.equals(kv2));
    }
    
}
