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

package com.boundary.plugin.sdk.bootstrap;

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

import com.boundary.plugin.sdk.PostExtract;

/**
 * Utility class for downloading a plugin from a release in GitHub
 */
public class DownloadPluginJar implements PostExtract {

	private final static String POM_PATH="pom.xml";
	private final static String JAR_DESTINATION_PATH="config/plugin.jar";
	private final static String VERSION_XPATH_EXPRESSION = "/project/version";
	private final static String BASE_URL_XPATH_EXPRESSION = "/project/properties/boundary-jar-base-url";
	private final static String JAR_BASE_NAME_EXPRESSION = "/project/name";
	private String version;
	private String baseUrl;
	private String jarBaseName;
	
	public DownloadPluginJar() {

	}
	
	/**
	 * Reads the pom.xml file to extract information to find the jar plugin.
	 * 
	 * @param pomFile Maven POM file path
	 * @throws ParserConfigurationException Indicates a failure to parse the pom.xml file
	 * @throws SAXException SAX parsing issue
	 * @throws XPathExpressionException Incorrect XPath expression to extract data
	 * @throws IOException Other kind of IO error
	 */
	private void readPOM() throws ParserConfigurationException,
			SAXException, XPathExpressionException, IOException {
		DocumentBuilder parser = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		try {
			FileInputStream input = new FileInputStream(new File(POM_PATH));
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

	/**
	 * Downloads a jar file based on pom.xml information
	 * to the configuration directory <code>config</code>
	 * 
	 * @throws IOException Any kind of IO error occurs
	 */
	private void downloadJAR() throws IOException {
		OutputStream out = null;
		try {
			HttpsURLConnection connection = null;
			String sUrl = String.format("%s/%s/plugin.jar",this.baseUrl,this.version);
			
			URL url = new URL(sUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.addRequestProperty("Accept","application/zip");
			// Ensure that we follow redirects
			HttpsURLConnection.setFollowRedirects(true);

			InputStream in = connection.getInputStream();

			out = new FileOutputStream(new File(JAR_DESTINATION_PATH));
			byte [] b = new byte[1024];
			
			//connection.connect();
			System.err.printf("Downloading jar of %d bytes from %s...%n",
					connection.getContentLength(),connection.getURL());
			System.err.printf("to %s%n",JAR_DESTINATION_PATH);
			int i = 1;
			while(in.read(b) != -1) {
				if (i % 1024 == 0) System.err.print(".");
				out.write(b);
				i++;
			}
			
			out.close();
		} catch (IOException e) {
			System.out.println("IO Exception");
			System.err.printf("%s%n",e.getMessage());
			System.err.flush();
			//out.close();
			throw e;
		}
	}
	
	/**
	 * Downloads the plugins jar file to the configuration directory <code>config/plugin.jar</code>
	 */
	public void execute(String[] args) {
		System.err.println("Running post extract...");
		try {
			readPOM();
			downloadJAR();
			System.err.println("Download successful");
		} catch (XPathExpressionException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (ParserConfigurationException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (SAXException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (FileNotFoundException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (MalformedURLException e) {
			System.err.printf("%s%n",e.getMessage());
		} catch (IOException e) {
			System.err.printf("%s%n",e.getMessage());
		}
		// Ensure that the standard error is flushed before exiting.
		System.err.flush();
	}
}
