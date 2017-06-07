// Copyright 2014-2015 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.plugin.sdk.jmx;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.plugin.sdk.jmx.extractor.AttributeValueExtractor;
import static org.junit.Assert.assertEquals;

public class AttributeValueExtractorTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValueDefaultScaling() {
        AttributeValueExtractor valueExtractor = new AttributeValueExtractor();
        int value = 2;
        MBeanAttribute attr = new MBeanAttribute();
        assertEquals(new Integer(2), valueExtractor.getValue(value, attr));
    }

    @Test
    public void testValueCustomScaling() {
        AttributeValueExtractor valueExtractor = new AttributeValueExtractor();
        Number value = 2;
        MBeanAttribute attr = new MBeanAttribute();
        attr.setScale(3);
        assertEquals(new Integer(6), valueExtractor.getValue(value, attr));
    }

}
