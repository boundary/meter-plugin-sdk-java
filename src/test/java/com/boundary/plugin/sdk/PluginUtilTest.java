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
	public void testMultipleSpaces() {
		assertEquals("Check MultipleSpaces","PS_SURVIVOR_SPACE",PluginUtil.toUpperUnderscore("PS Survivor Space",'_'));
	}
}
