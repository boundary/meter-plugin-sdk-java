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

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.plugin.sdk.MetricDefinition;
import com.boundary.plugin.sdk.MetricDefinitionList;

import com.google.gson.Gson;

public class MBeansTransformerTest {
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private MetricDefinitionTransform transform;
	private JMXClient client;
	private MBeansTransformer<MetricDefinitionList> transformer;

	@Before
	public void setUp() throws Exception {
		transform = new MetricDefinitionTransform();
		client = new JMXClient();
		client.connect("localhost", 9991);
		transformer = new MBeansTransformer<MetricDefinitionList>(client,transform,"FOO");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testMetricDefinitionConstructor() {
		MetricDefinitionTransform transform = new MetricDefinitionTransform();
		JMXClient client = new JMXClient();
		try {
			client.connect("localhost", 9991);
			MBeansTransformer<MetricDefinitionList> transformer = new MBeansTransformer<MetricDefinitionList>(
					client, transform, "FOO");
			transformer.transform();

			MetricDefinitionList list = transform.getExport();
			ArrayList<MetricDefinition> a = list.getResult();

			for (MetricDefinition def : a) {
				System.out.println(def);
			}
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	@Test
	public void testMetricDefinitionTransform() {
		transformer.transform();
		MetricDefinitionList list = transform.getExport();

		Gson mapper = new Gson();
		mapper.toJson(list, System.out);
	}
	
	@Test
	public void testMBeansTransformer() {
		MBeanTransform<MBeanMap> transform = new MBeanMapTransform();
		JMXClient client = new JMXClient();
		try {
			client.connect("localhost", 9991);
			MBeansTransformer<MBeanMap> transformer = new MBeansTransformer<MBeanMap>(
					client, transform, "FOO");
			transformer.transform();

			MBeanMap map = transformer.export();
			for (MBeanEntry entry : map.getMap()) {
				System.out.println(entry);
			}

			Gson gson = new Gson();
			gson.toJson(map, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
