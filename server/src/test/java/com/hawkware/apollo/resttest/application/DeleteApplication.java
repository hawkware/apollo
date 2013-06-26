package com.hawkware.apollo.resttest.application;
import static com.jayway.restassured.RestAssured.given;

import com.hawkware.apollo.resttest.util.*;

import java.util.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DeleteApplication {
	
	private static List<String[]> environments;
	private static Map<String,List<Property>> applications;
	private static String[] appNames = {"sample-app-on", "sample-app-tw"};

	@BeforeClass
	public static void setUpClass(){
		Util.setUp();
		environments = new ArrayList<String[]>();
		environments.add(new String[] {
				"dev","local.com","192.168.0.1"});
		environments.add(new String[] {
				"test", "test.com","0.0.0.0"});
		environments.add(new String[] {
				"production","production.com", "192.168.0.2"});
		environments.add(new String[] {
				"replicant", "replicant.com", "192.168.0.3"});
		//create the environments to be used for testing
		for(String[] environment: environments){
			Util.createEnvironment(
					environment[0], environment[1], environment[2]);
		}
		
		Map<String, String> propertyValues = new HashMap<String, String>();
		propertyValues.put(environments.get(0)[0], environments.get(0)[1]);
		propertyValues.put(environments.get(1)[0], environments.get(1)[1]);
		
		List<Property> properties = new ArrayList<Property>();
		properties.add(new Property("db.server", "84600", propertyValues));
		
		propertyValues = new HashMap<String, String>();
		propertyValues.put(environments.get(2)[0], environments.get(2)[1]);
		propertyValues.put(environments.get(3)[0], environments.get(3)[1]);
		properties.add(new Property("db.user", "84600", propertyValues));
		
		applications = new HashMap<String, List<Property>>();
		applications.put(appNames[0], properties);
		Util.createApplication(appNames[0], properties);
		
		applications.put(appNames[1], properties);
		Util.createApplication(appNames[1],properties);
	}

	@Test
	public void shouldDeleteApplication(){
		Random rand = new Random();
		String appName = appNames[rand.nextInt(appNames.length)];
		given().expect().statusCode(200).delete("/application/"+appName);
	}

	@Test
	public void shouldFailToDeleteNonExistingApplication(){
		String appName = "Randomness";
		given().expect().statusCode(404).delete("/application/"+appName);
	}
	
	@AfterClass
	public static void tearDownClass(){
		//delete applications
		Set<String> apps = applications.keySet();
		for(String key: apps){
			given().delete("/application/"+key);
		}
		
		//delete environments
		for(String[] environment: environments){
			given().delete("/environment/" + environment[0]);
		}
	}
}