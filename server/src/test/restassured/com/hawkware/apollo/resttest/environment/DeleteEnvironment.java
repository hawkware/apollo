package com.hawkware.apollo.resttest.environment;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.given;

import com.hawkware.apollo.resttest.Util.*;

public class DeleteEnvironment {

	private static String[] environment = {"test", "test.com", "1.1.1.1"};
	
	@BeforeClass
	public static void setUp(){
		Util.setUp();
		Util.createEnvironment(environment[0], environment[1], environment[2]);
	}
	
	/*
	 * Make sure that an existing environment can be delete with a call to the
	 * endpoint /environment/[name]. [name] being the name of the environment 
	 * to delete
	 */
	@Test
	public void shouldWorkWithExistingEnvironment(){
		String url = "/environment/"+environment[0];
		given().expect().statusCode(200).delete(url);
	}
	
	/*
	 * Make sure that trying to delete an non existing environment with the call
	 * to /environment/[name], where [name] is the name of the non-existing 
	 * environment, fails with the status code 404
	 */
	@Test
	public void shouldFailWithNonExistingEnvironment(){
		String url = "/environment/" + environment[0] + "w";
		given().expect().statusCode(404).delete(url);
	}
	
	@AfterClass
	public static void tearDown(){
		given().delete("/environment/" + environment[0]);
	}
}
