// Copyright 2014 Boundary, Inc.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class GetPluginJar implements PostInstall {
	
	private static Logger LOG = LoggerFactory.getLogger(PluginUtil.class);

	private final String POM_PATH="pom.xml";
	private final String JAR_DESTINATION_PATH="config/plugin.json";
	private final String VERSION_XPATH_EXPRESSION = "/project/version";
	private final String BASE_URL_XPATH_EXPRESSION = "/project/properties/boundary-jar-base-url";
	private final String JAR_BASE_NAME_EXPRESSION = "/project/name";
	private String version;
	private String baseUrl;
	private String jarBaseName;
	
	public GetPluginJar() {

	}
	
	public void readPOM(String pomFile)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		FileInputStream input = new FileInputStream(new File(pomFile));
		Document document = parser.parse(input);

		XPath xpath = XPathFactory.newInstance().newXPath();
		this.version  = (String)xpath.evaluate(VERSION_XPATH_EXPRESSION,document,XPathConstants.STRING);
		this.baseUrl = (String)xpath.evaluate(BASE_URL_XPATH_EXPRESSION,document,XPathConstants.STRING);
		this.jarBaseName = (String)xpath.evaluate(JAR_BASE_NAME_EXPRESSION,document,XPathConstants.STRING);
		System.out.printf("jarBaseName: %s\n",this.jarBaseName);

		LOG.debug("baseUrl: {}, version: {}, jarBaseName",this.baseUrl,this.version,this.jarBaseName);
	}		
	public void downloadJAR() {
		OutputStream out = null;
		try {
			HttpsURLConnection connection = null;
			String sUrl = String.format("%s/%s/%s-%s",this.baseUrl,this.version,this.jarBaseName,this.version);
			System.out.println(sUrl);
			
			URL url = new URL("https://github.com/boundary/boundary-plugin-jvm/releases/download/RE-00.07.00/boundary-plugin-jvm-00.07.00.jar");
			connection = (HttpsURLConnection) url.openConnection();
			connection.addRequestProperty("Accept","application/zip");
			HttpsURLConnection.setFollowRedirects(true);

			
			InputStream in = connection.getInputStream();
			out = new FileOutputStream(new File(JAR_DESTINATION_PATH));
			byte [] b = new byte[1024];
			connection.connect();
			
//			int contentLength = connection.getContentLength();
//			System.out.printf("length = %d\n",contentLength);
//			System.out.printf("content-type = %s\n",connection.getContentType());
			
			while(in.read(b) != -1) {
				out.write(b);
			}
			out.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	

	
	public void execute(String[] args) {
		
		try {
			readPOM(POM_PATH);
			//downloadJAR();
		} catch (XPathExpressionException e) {
			LOG.error("XPathExpressionException: ",e.getMessage());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String [] args) {
		GetPluginJar gpJar = new GetPluginJar();
		gpJar.execute(args);
	}

}
