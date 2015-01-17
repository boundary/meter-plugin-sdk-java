#!/bin/bash

mvn -q exec:java -Dexec.mainClass=com.boundary.plugin.sdk.jmx.ExportMBeans -Dexec.args="$*"

