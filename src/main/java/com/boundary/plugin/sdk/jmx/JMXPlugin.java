//Copyright 2014-2015 Boundary, Inc.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package com.boundary.plugin.sdk.jmx;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.plugin.sdk.CollectorDispatcher;
import com.boundary.plugin.sdk.Constants;
import com.boundary.plugin.sdk.MeasurementSink;
import com.boundary.plugin.sdk.EventSink;
import com.boundary.plugin.sdk.Plugin;
import com.boundary.plugin.sdk.PluginJSON;
import com.boundary.plugin.sdk.jmx.extractor.AttributeValueExtractor;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class JMXPlugin implements Plugin<JMXPluginConfiguration> {

    private static Logger LOG = LoggerFactory.getLogger(JMXPlugin.class);

    private JMXPluginConfiguration configuration;
    private CollectorDispatcher dispatcher;
    private MeasurementSink output;
    private EventSink eventOutput;
    private MBeanMap mbeanMap;
    private PluginJSON manifest;
    private AttributeValueExtractor valueExtractor;
    private final String MBEAN_MAP_PATH = "config/mbeans.json";
    private final String PLUGIN_MANIFEST_PATH = "plugin.json";
    private final String PLUGIN_PARAM_PATH = "param.json";

    @Override
    public void setMeasureOutput(MeasurementSink output) {
        LOG.info("setting measureoutput");
        this.output = output;
        this.valueExtractor = new AttributeValueExtractor();
    }

    @Override
    public void setEventOutput(EventSink output) {
        LOG.info("setting eventoutput");
        this.eventOutput = output;
    }

    public PluginJSON getManifest() {
        return this.manifest;
    }

    @Override
    public void setConfiguration(JMXPluginConfiguration configuration) {
        this.configuration = configuration;
    }

    public void loadConfiguration() {
        try {
            Gson gson = new Gson();
            String param = System.getenv(Constants.TSP_PLUGIN_PARAMS);
            JMXPluginConfiguration configuration = null;
            if (param == null || param == "") {
                configuration = JMXPluginConfiguration.getConfiguration(new FileReader(PLUGIN_PARAM_PATH));
            } else {
                configuration = gson.fromJson(param, JMXPluginConfiguration.class);
            }
            setConfiguration(configuration);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            mbeanMap = gson.fromJson(new FileReader(MBEAN_MAP_PATH), MBeanMap.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            Gson gson = new Gson();
            manifest = gson.fromJson(new FileReader(PLUGIN_MANIFEST_PATH), PluginJSON.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDispatcher(CollectorDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        ArrayList<JMXPluginConfigurationItem> items = configuration.getItems();
        for (JMXPluginConfigurationItem i : items) {
            dispatcher.addCollector(new JMXCollector(this, i.getName(), i, mbeanMap, valueExtractor, output, eventOutput));
        }
        dispatcher.run();
    }
}
