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

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class GetPluginJar implements PostInstall {

	private final static String POM_PATH="pom.xml";
	private final static String JAR_DESTINATION_PATH="config/plugin.jar";
	private final static String VERSION_XPATH_EXPRESSION = "/project/version";
	private final static String BASE_URL_XPATH_EXPRESSION = "/project/properties/boundary-jar-base-url";
	private final static String JAR_BASE_NAME_EXPRESSION = "/project/name";
	private String version;
	private String baseUrl;
	private String jarBaseName;
	
	public GetPluginJar() {

	}
	
	public void readPOM(String pomFile) throws ParserConfigurationException,
			SAXException, XPathExpressionException, IOException {
		DocumentBuilder parser = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		try {
			FileInputStream input = new FileInputStream(new File(pomFile));
			try {
				Document document = parser.parse(input);

				XPath xpath = XPathFactory.newInstance().newXPath();
				this.version = (String) xpath.evaluate(
						VERSION_XPATH_EXPRESSION, document,
						XPathConstants.STRING);
				this.baseUrl = (String) xpath.evaluate(
						BASE_URL_XPATH_EXPRESSION, document,
						XPathConstants.STRING);
				this.jarBaseName = (String) xpath.evaluate(
						JAR_BASE_NAME_EXPRESSION, document,
						XPathConstants.STRING);
				input.close();
			} catch (IOException e) {
				throw e;
			} finally {
				input.close();
			}
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	public void downloadJAR() throws IOException {
		OutputStream out = null;
		try {
			HttpsURLConnection connection = null;
			String sUrl = String.format("%s/%s/%s-%s.jar",this.baseUrl,this.version,this.jarBaseName,this.version);
			
			URL url = new URL(sUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.addRequestProperty("Accept","application/zip");
			// Ensure that we follow redirects
			HttpsURLConnection.setFollowRedirects(true);

			InputStream in = connection.getInputStream();
			System.err.printf("Downloading jar from %s%n",connection.getURL());
			out = new FileOutputStream(new File(JAR_DESTINATION_PATH));
			byte [] b = new byte[1024];
			connection.connect();
			
			while(in.read(b) != -1) {
				System.err.printf(".");
				out.write(b);
			}
			
			out.close();

		} catch (FileNotFoundException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (MalformedURLException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (IOException e) {
			System.err.printf("%s%n",e.getMessage());
			out.close();
			throw e;
		}
	}
	
	public void execute(String[] args) {
		System.err.println("Executing post extract...");
		try {
			readPOM(POM_PATH);
			downloadJAR();
		} catch (XPathExpressionException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (ParserConfigurationException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (SAXException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (IOException e) {
			System.err.printf("%s%n",e.getMessage());
		}
		System.err.flush();
	}
	
	public static void main(String [] args) {
		GetPluginJar gpJar = new GetPluginJar();
		gpJar.execute(args);
	}
}
