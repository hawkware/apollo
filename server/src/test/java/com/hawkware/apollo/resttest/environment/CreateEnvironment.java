package com.hawkware.apollo.resttest.environment;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import com.hawkware.apollo.resttest.util.Util;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasXPath;

public class CreateEnvironment {
	
	private static String hostName = "localhost";
	private static String ipAddress = "127.0.0.1";
	
	@BeforeClass
	public static void setUp(){
		Util.setUp();
	}

	/*
	 * Make sure that the default xml structure works for creating an environment
	 * . eg
	 * <environment name=[name]>
	 * 	<server>
	 * 		<hostName>[hostName]</hostName>
	 * 		<ipAddress>[ipAddress]</ipAddress>
	 * 	</server>
	 * </environment>
	 */
	@Test
	public void shouldWorkWithCompleteXml(){
		String xml = "<environment name='local'><server><hostName>%s" +
				"</hostName><ipAddress>%s</ipAddress></server></environment>";
		xml = String.format(
					xml, 
					CreateEnvironment.hostName, 
					CreateEnvironment.ipAddress
				);
		given().header("Content-Type","application/xml").
			body(xml).expect().body(hasXPath("/environment/server/hostName")).
			and().body(hasXPath("/environment/server/ipAddress")).
		post("/environment");
	}

	/*
	 * Make sure that a valid xml structure without a host name still works for
	 * creating an environment eg.
	 * <environment name=[name]>
	 * 	<server>
	 * 		<ipAddress>[ipAddress]</ipAddress>
	 * 	</server>
	 * </environment>
	 */
	@Test
	public void shouldWorkForIncompleteXmlWithoutHostName(){
		String xml = "<environment name='dev'><server><ipAddress>%s" +
				"</ipAddress></server></environment>";
		xml = String.format(xml, CreateEnvironment.ipAddress);
		given().header("Content-Type","application/xml").body(xml).expect().
		body(hasXPath("/environment/server/ipAddress")).post("/environment");
	}
	
	/*
	 * Make sure that a valid xml structure without an ip address still works
	 * for creating an environment eg.
	 * <environment name=[name]>
	 * 	<server>
	 * 		<hostName>[hostName]</hostName>
	 * 	</server>
	 * </environment>
	 */
	@Test
	public void shouldWorkForIncompleteXmlWithoutIpAddress(){
		String xml = "<environment name='dev2'><server><hostName>%s" +
				"</hostName></server></environment>";
		xml = String.format(xml, CreateEnvironment.hostName);
		given().header("Content-Type","application/xml").body(xml).expect().
		body(hasXPath("/environment/server/hostName")).post("/environment");
	}
	
	/*
	 * Make sure that an invalid xml structure with a null hostName element 
	 * doesn't work for creating an environment eg
	 * <environment name=[name]>
	 * 	<server>
	 * 		<hostName></hostName>
	 * 		<ipAddress>[IpAddress]</ipAddress>
	 * 	<server>
	 * </environment>
	 */
	@Test
	public void shouldNotWorkWithNullHostName(){
		String xml = "<environment name='dev3'><server><ipAddress>%s" +
				"</ipAddress><hostName></hostName></server></environment>";
		xml = String.format(xml,CreateEnvironment.ipAddress);
		given().header("Content-Type", "application/xml").body(xml).expect().
		statusCode(400).post("/environment");
	}
	
	/*
	 * Make sure that an invalid xml structure with a null ipAddress element 
	 * doesn't work for creating an environment eg.
	 * <environment name=[name]>
	 * 	<server>
	 * 		<hostName>[hostName]</hostName>
	 * 		<ipAddress></ipAddress>
	 * 	</server>
	 * </environment>
	 */
	@Test
	public void shouldNotWorkWithNullIpAddress(){
		String xml = "<environment name='dev4'><server><ipAddress>" +
				"</ipAddress><hostName>%s</hostName></server></environment>";
		xml = String.format(xml, CreateEnvironment.hostName);
		given().header("Content-Application","application/xml").body(xml).
		expect().statusCode(400).post("/environment");
	}
	
	/*
	 * Make sure that an invalid xml structure with null ipAddress and hostName
	 * elements doesn't work for creating an environment eg.
	 * <environment name=[name]>
	 * 	<server>
	 * 		<ipAddress></ipAddress>
	 * 		<hostName></hostName>
	 * 	</server>
	 * </environment>
	 */
	@Test
	public void shouldNotWorkWithNullHostNameAndIpAddress(){
		String xml = "<environment name='dev5'><server><ipAddress></ipAddress>" +
				"<hostName></hostName></server></environment>";
		given().header("Content-Type","application/xml").body(xml).expect().
		statusCode(400).post("/environment");		
	}
	
	/*
	 * Make sure that an invalid xml structure with a null server element doesn't
	 * work for creating an environment eg.
	 * <environment name=[name]>
	 * 	<server></server>
	 * </environment>
	 */
	@Test
	public void shouldNotWorkWithoutHostNameAndIpAddressElements(){
		String xml = "<environment name='dev6'><server></server></environment";
		given().header("Content-Type","application/xml").body(xml).expect().
		statusCode(400).post("/environment");
	}
	
	/*
	 * Make sure that an invalid xml structure with it environment element having
	 * no name attribute doesn't work for creating an environment eg
	 * <environment>
	 * 	<server>
	 * 		<ipAddress>[ipAddress]</ipAddress>
	 * 		<hostName>[hostName]</hostName>
	 * 	</server>
	 * </environment>
	 */
	@Test
	public void shouldNotWorkWithoutNameAttributeOfEnvironment(){
		String xml = "<environment><server><ipAddress>%s</ipAddress><hostName>"+
				"%s</hostName></server></environment>";
		xml = String.format(
					xml, CreateEnvironment.ipAddress, 
					CreateEnvironment.hostName
				);
		given().header("Content-Type","application/xml").body(xml).expect().
		statusCode(400).post("/environment");
	}
	
	@AfterClass
	public static void tearDown(){
		//temporary fix
//		String[] environments = {
//					"local", "dev", "dev2", "dev3", "dev4", "dev5",
//					"dev6"
//				};
//		for(String environment : environments){
//			String url = "/environment/%s";
//			url = String.format(url, environment);
//			given().header("Content-Type","application/xml").delete(url);	
//		}
	}
}