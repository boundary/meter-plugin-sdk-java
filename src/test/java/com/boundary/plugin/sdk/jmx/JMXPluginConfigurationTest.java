// Copyright 2014-2015 BMC Software, Inc.
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

import com.boundary.plugin.sdk.jmx.JMXPluginConfiguration;
import com.boundary.plugin.sdk.jmx.JMXPluginConfigurationItem;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JMXPluginConfigurationTest {

    @Test
    public void testDeserialization () {
        final String jsonString = "{ pollInterval: 2000, items: [ { name: 'myName', host: 'localhost', port: 1234, user: 'myUser', password: 'myPassword', pollInterval: 1000, source: 'mySource' } ]  }";

        JMXPluginConfiguration config = JMXPluginConfiguration.getConfiguration(jsonString);
        assertNotNull(config);
        assertEquals(2000, config.getPollInterval());
        assertEquals(1, config.getItems().size());
        assertEquals("myName", config.getItems().get(0).getName());
        assertEquals("localhost", config.getItems().get(0).getHost());
        assertEquals(1234, config.getItems().get(0).getPort());
        assertEquals("myUser", config.getItems().get(0).getUser());
        assertEquals("myPassword", config.getItems().get(0).getPassword());
        assertEquals("1000", config.getItems().get(0).getPollInterval());
        assertEquals("mySource", config.getItems().get(0).getSource());
    }

}
