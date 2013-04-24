package com.hawkware.apollo.resttest.util;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;

public class Util {

	public static void setUp(){
		RestAssured.baseURI = "http://0.0.0.0:9090/apollo";
	}

	/**
	 * This is a utility function for create new environment for testing
	 * @param name	//name of the environment that should be created
	 * @param hostName	//host name of the server on which the environment is
	 * @param ipAddress	//the ip of the server on which the environment is
	 * @return	//returns the string representation of the environment created
	 */
	public static String createEnvironment(
			String name, String hostName, String ipAddress){
		
		Util.setUp();
		String xml = Util.formatEnvironmentQuery(name, hostName, ipAddress);
		return given().header("Content-Type","application/xml").body(xml).
				post("/environment").andReturn().asString();
	}
	
	/**
	 * This is a utility function for formatting the variables that are passed
	 * to it into a string representation of the xml structure required to 
	 * create a new environment
	 * @param name	//name of the environment that should be created
	 * @param hostName	//host name of the server on which the environment is
	 * @param ipAddress	//the ip of the server on which the environment is
	 * @return	//returns the string representation of the environment created
	 */
	public static String formatEnvironmentQuery(
			String name, String hostName, String ipAddress){
		String xml = "";
		if(name != null){
			xml += String.format("<environment name=\"%s\">", name);
		}
		xml += "<server>";
		if(hostName != null){
			xml += String.format("<hostName>%s</hostName>", hostName);
		}
		if(ipAddress != null){
			xml += String.format("<ipAddress>%s</ipAddress>", ipAddress);
		}
		xml += "</server></environment>";
		return xml;
	}
}