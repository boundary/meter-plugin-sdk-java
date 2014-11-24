package com.boundary.plugin.sdk;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONToJava {

	public JSONToJava() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String []args) {
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		try {
			User user = mapper.readValue(new File("user.json"), User.class);
			System.out.println(user.getName().getLast());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
