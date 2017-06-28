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

import com.boundary.plugin.sdk.MeasurementSinkFactory.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginRunner {

    private static final Logger LOG = LoggerFactory
            .getLogger(PluginRunner.class);
    private String className;
    private CollectorDispatcher dispatcher;

    /**
     * Default constructor
     *
     * @param className Class name of the plugin to load
     */
    public PluginRunner(String className) {
        this.className = className;
        this.dispatcher = new CollectorDispatcher();
    }

    /**
     * Outputs usage to standard error
     */
    private static void Usage() {
        LOG.error("%s <class name>%n", PluginRunner.class.toString());
        System.exit(1);
    }

    /**
     * Loads the plugin class and calls its run() method.
     */
    public void run() {
        Plugin plugin = null;
        try {
            plugin = (Plugin) Class.forName(this.className).newInstance();
            plugin.setDispatcher(dispatcher);
            plugin.loadConfiguration();
            plugin.setMeasureOutput(MeasurementSinkFactory.getInstance(Type.API));
            plugin.setEventOutput(new EventSinkAPI());
            plugin.run();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOG.error("Exception occured while loading plugin class method {}", e);
        }
    }

    /**
     * Entry point for running the plugin
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            Usage();
            System.exit(1);
        }
        PluginRunner plugin = new PluginRunner(args[0]);
        plugin.run();
    }
}
