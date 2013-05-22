package com.hawkware.apollo.resttest.environment;
import com.hawkware.apollo.resttest.Util.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.given;

public class ListEnvironments {

	private static List<String[]> environments = new ArrayList<String[]>();
	
	@BeforeClass
	public static void setUp(){
		Util.setUp();
		environments.add(new String[] {"dev", "dev.com", "127.0.01"});
		environments.add(new String[] {"local", "local.com", "0.0.0.0"});
		environments.add(new String[] {
				"production", "production.com", "192.168.0.1"});
		for(String [] environment: environments){
			Util.createEnvironment(
					environment[0], environment[1], environment[2]);
		}
	}
	
	/*
	 * Make sure that a call to the /environments end point returns the list of
	 * all the environments that are currently in the system 
	 */
	@Test
	public void shouldListAllEnvironments(){
		String resultString = given().get("/environments").andReturn().
				asString();
		boolean result = true;
		for(String[] environment: environments){
			if(resultString.contains(Util.formatEnvironmentQuery(
					environment[0], environment[1], environment[2]))){
				result = result ? true : false;
			}else{
				result = false;
			}
		}
		Assert.assertTrue(result);
	}
	
	/*
	 * make sure that a call to the environment/[name] end point returns the 
	 * details of the named environment.
	 */
	@Test
	public void shouldGetSpecificEnvironment(){
		String[] environment = environments.get(
				new Random().nextInt(environments.size()));
		String url = "/environment/" + environment[0];
		String resultString = given().get(url).andReturn().asString();
		String expectedResultString = "<?xml version=\"1.0\" encoding=" +
				"\"UTF-8\" standalone=\"yes\"?>"; 
		expectedResultString += Util.formatEnvironmentQuery(
				environment[0], environment[1], environment[2]);
		Assert.assertTrue(expectedResultString.equalsIgnoreCase(resultString));
	}
	
	/*
	 * make sure that the call to environment/[name] end point fails and returns
	 * a status code of 404 
	 */
	@Test
	public void shouldNotWorkWithNonExistingEnvironmentName(){
		String[] environment = environments.get(
				new Random().nextInt(environments.size()));
		String url = "/environment/" + environment[0] + "w";
		given().expect().statusCode(404).get(url);
	}
	
	@AfterClass
	public static void tearDown(){
		for(String[] environment: environments){
			String url = "/environment/"+ environment[0];
			given().body(url).delete(url);
		}
	}
}