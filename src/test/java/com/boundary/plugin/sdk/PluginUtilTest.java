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

package com.boundary.plugin.sdk;

import static org.junit.Assert.*;

import org.junit.Test;

public class PluginUtilTest {

	@Test
	public void testAllLowerCase() {
		assertEquals("Check AllLowerCase","FOOBAR",PluginUtil.toUpperUnderscore("foobar",'_'));
	}
	
	@Test
	public void testAllUpperCase() {
		assertEquals("Check AllUpperCase","FOOBAR",PluginUtil.toUpperUnderscore("FOOBAR",'_'));
	}
	
	@Test
	public void testMixedUpperCase() {
		assertEquals("Check MixedUpperCase","FOO_BAR",PluginUtil.toUpperUnderscore("FooBar",'_'));
	}
	
	@Test
	public void testLowerCaseWithSpace() {
		assertEquals("Check MixedUpperCase","FOO_BAR",PluginUtil.toUpperUnderscore("foo bar",'_'));
	}
	
	@Test
	public void testUpperCaseWithSpace() {
		assertEquals("Check MixedUpperCase","FOO_BAR",PluginUtil.toUpperUnderscore("FOO BAR",'_'));
	}
	
	@Test
	public void testMixCaseWithSpace1() {
		assertEquals("Check MixedUpperCase","FOO_BAR",PluginUtil.toUpperUnderscore("foo BAR",'_'));
	}
	
	@Test
	public void testMixCaseWithSpace2() {
		assertEquals("Check MixedUpperCase","FOO_BAR",PluginUtil.toUpperUnderscore("FOO bar",'_'));
	}
	
	@Test
	public void testLeadingSpace() {
		assertEquals("Check MixedUpperCase","FOO_BAR",PluginUtil.toUpperUnderscore(" FOO BAR",'_'));
	}
	
	@Test
	public void testTrailingSpace() {
		assertEquals("Check MixedUpperCase","FOO_BAR",PluginUtil.toUpperUnderscore("FOO BAR ",'_'));
	}
	
	@Test
	public void testMulipleSpace() {
		assertEquals("Check MultipleSpace","PS_SURVIVOR_SPACE",PluginUtil.toUpperUnderscore("PS Survivor Space",'_'));
	}
	
	@Test
	public void testCamelCaseToSpace() {
		assertEquals("Check MultipleSpace","Collection Usage Threshold",PluginUtil.camelCaseToSpaceSeparated("CollectionUsageThreshold"));
	}
}
