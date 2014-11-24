package com.boundary.plugin.sdk.jmx.hello;



public interface HelloWorldMBean
{
  public void setGreeting( String greeting );
  public String getGreeting();
  public void printGreeting();
}