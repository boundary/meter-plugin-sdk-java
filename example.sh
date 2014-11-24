#!/bin/bash
java -cp target/test-classes -Dcom.sun.management.jmxremote.port=9991 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false com.boundary.plugin.sdk.jmx.ExampleAgent
