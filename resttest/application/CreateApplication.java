package com.hawkware.apollo.resttest.application;
import java.util.*;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.After;
import static com.jayway.restassured.RestAssured.given;
import com.hawkware.apollo.resttest.util.*;

public class CreateApplication {

	private static List<String[]> environments;
	private static String applicationName = "sample-app";
	
	@BeforeClass
	public static void setUpClass(){
		Util.setUp();
		environments = new ArrayList<String[]>();
		environments.add(new String[] {"dev", "localhost.com", "0.0.0.0"});
		environments.add(new String[] {
				"local", "example.localhost.com", "127.0.0.1"});
		environments.add(new String[] {
				"test", "test.localhost.com", "192.168.0.1"});
		environments.add(new String[] {
				"production", "production.localhost.com", "192.168.0.2"});
		//create environments
		for(String[] environment: environments){
			Util.createEnvironment(
					environment[0], environment[1], environment[2]);
		}
	}

	@After
	public void tearDown(){
		given().header("Content-Type", "application/xml").
		delete("/application/"+applicationName);
	}

	/*
	 * Make sure that creating an application with a complete xml structre works
	 * eg.
	 * <application name="sample-app">
	 *	<property name="db.server" timeToLive="86400">
	 *		<value environment="dev">db.dev.company.corp</value>
	 *		<value environment="local">localhost</value>
	 *	</property>
	 *	<property name="db.user" timeToLive="86400">
	 *		<value environment="default">sa</value>
	 *	</property>
	 *	<property name="db.password" timeToLive="86400">
	 *		<value environment="dev">p4ssw0rd</value>
	 * 		<value environment="local">Password1</value>
	 *	</property>
	 *	<property name="admin.email" timeToLive="86400">
	 *		<value environment="dev">admin@company.com</value>
	 *		<value environment="local">admin@local</value>
	 *	</property>
	 *</application>
	 */
	@Test
	public void shouldWorkWithCompleteApplicationXml(){
		Map<String,String> values = new HashMap<String, String>();
		values.put(environments.get(0)[0], environments.get(0)[1]);
		values.put(environments.get(1)[0], environments.get(1)[1]);

		List<Property> properties = new ArrayList<Property>();
		properties.add(new Property("db.server","86400",values));

		values = new HashMap<String, String>();
		values.put("default", "sa");
		properties.add(new Property("db.user", "86400", values));

		values = new HashMap<String, String>();
		values.put("dev","p4ssw0rd");
		values.put("local","Password1");
		properties.add(new Property("db.password", "86400", values));

		values = new HashMap<String, String>();
		values.put("dev", "admin@company.com");
		values.put("local", "admin@local");
		properties.add(new Property("admin.email","86400", values));

		String xml = Util.createApplication(applicationName, properties);
		String expectedResult = Util.formatApplicationQuery(
				applicationName, properties);
		expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone" +
				"=\"yes\"?>" + expectedResult;
		Assert.assertEquals(expectedResult, xml);
	}
	
	/*
	 * Makes sure that trying to create an application with just the application
	 * xml element without any other elements fails
	 * e.g.
	 * <application name='[name]'>
	 * </application>
	 */
	@Test
	public void shouldNotWorkWithoutApplicationContent(){
		List<Property> properties = new ArrayList<Property>();
		String xml = Util.formatApplicationQuery(applicationName, properties);
		given().header("Content-Type", "application/xml").body(xml).expect().
		statusCode(404).post("/application");
	}
	
	/*
	 * Makes sure that creating two applications with the same name doesn't work
	 */
	@Test
	public void shouldNotWorkCreatingDuplicationApplicationSameName(){
		Map<String,String> values = new HashMap<String, String>();
		values.put(environments.get(0)[0], environments.get(0)[1]);
		values.put(environments.get(1)[0], environments.get(1)[1]);

		List<Property> properties = new ArrayList<Property>();
		properties.add(new Property("db.server","86400",values));

		values = new HashMap<String, String>();
		values = new HashMap<String, String>();
		values.put("default", "sa");
		properties.add(new Property("db.user", "86400", values));

		values = new HashMap<String, String>();
		values.put("dev","p4ssw0rd");
		values.put("local","Password1");
		properties.add(new Property("db.password", "86400", values));
	
		String xml = Util.createApplication(applicationName, properties);
		xml = Util.formatApplicationQuery(applicationName, properties);
		given().header("Content-Type","application/xml").body(xml).expect().
		statusCode(404).post("/application");
	}
	
	/*
	 * Makes sure that creating an application without a name doesn't work
	 * <application name=' '>
	 *	<property name="db.server" timeToLive="86400">
	 *		<value environment="dev">db.dev.company.corp</value>
	 *		<value environment="local">localhost</value>
	 *	</property>
	 * </application>
	 */
	@Test
	public void shouldNotWorkWithoutApplicationName(){
		Map<String,String> values = new HashMap<String, String>();
		values.put(environments.get(0)[0], environments.get(0)[1]);
		values.put(environments.get(1)[0], environments.get(1)[1]);

		List<Property> properties = new ArrayList<Property>();
		properties.add(new Property("db.server","86400",values));
		String xml = Util.formatApplicationQuery(" ", properties);
		given().header("Content-Type", "application/xml").body(xml).expect().
		statusCode(404).post("/application");
	}
	
	/*
	 * Makes sure that creating an application with a property without a name 
	 * doesn't work e.g.
	 * <application name=[name]>
	 *	<property name=" " timeToLive="86400">
	 *		<value environment="dev">db.dev.company.corp</value>
	 *		<value environment="localhost">localhost</value>
	 *	</property>
	 * </application>
	 */
	@Test
	public void shouldNotWorkWithoutPropertyName(){
		Map<String,String> values = new HashMap<String, String>();
		values.put(environments.get(0)[0], environments.get(0)[1]);
		values.put(environments.get(1)[0], environments.get(1)[1]);

		List<Property> properties = new ArrayList<Property>();
		properties.add(new Property(" ","86400",values));
		String xml = Util.formatApplicationQuery(applicationName, properties);
		given().header("Content-Type", "application/xml").body(xml).expect().
		statusCode(404).post("/application");		
	}

	/*
	 * Makes sure that creating an application with a property that has no value
	 * doesn't work
	 * <application name=[name]>
	 *	<property name="db.server" timeToLive="86400">
	 *		<value environment="dev">db.dev.company.corp</value>
	 *		<value environment="local"></value>
	 *	</property>
	 * </application>
	 */
	@Test
	public void shouldNotWorkWithoutPropertyContent(){
		Map<String,String> values = new HashMap<String, String>();
		List<Property> properties = new ArrayList<Property>();
		properties.add(new Property("db.server","86400",values));
		String xml = Util.formatApplicationQuery(applicationName, properties);
		given().header("Content-Type", "application/xml").body(xml).expect().
		statusCode(404).post("/application");		
	}

	/*
	 * Makes sure that creating an application with multiple properties with the
	 * same name doesn't work. e.g.
	 * <application name=[name]>
	 *	<property name="db.server" timeToLive="86400">
	 *		<value environment="dev">db.dev.company.corp</value>
	 *		<value environment="local">localhost</value>
	 *	</property>
	 *	<property name="db.server" timeToLive="86400">...</property>
	 * </application>
	 */
	@Test
	public void shouldNotWorkWithDuplicatePropertyName(){
		Map<String,String> values = new HashMap<String, String>();
		values.put(environments.get(0)[0], environments.get(0)[1]);
		values.put(environments.get(1)[0], environments.get(1)[1]);

		List<Property> properties = new ArrayList<Property>();
		properties.add(new Property("db.server","86400",values));

		values = new HashMap<String, String>();
		values.put("default", "sa");
		properties.add(new Property("db.server", "86400", values));

		values = new HashMap<String, String>();
		values.put("dev","p4ssw0rd");
		values.put("local","Password1");
		properties.add(new Property("db.password", "86400", values));
		String xml = Util.formatApplicationQuery(applicationName, properties);
		given().header("Content-Type", "application/xml").body(xml).expect().
		statusCode(404).post("/application");		
	}
	
	/*
	 * Makes sure that creating an application with a property without inner 
	 * elements doesn't work e.g.
	 * <application name=[name]>
	 *	<property name="db.server" timeToLive="86400"></property> 
	 * </application> 
	 */
	@Test
	public void shouldNotWorkWithoutPropertyValuesName(){
		Map<String,String> values = new HashMap<String, String>();
		values.put(environments.get(0)[0], environments.get(0)[1]);
		values.put(" ", "localhost");

		List<Property> properties = new ArrayList<Property>();
		properties.add(new Property("db.server","86400",values));
		String xml = Util.formatApplicationQuery(applicationName, properties);
		given().header("Content-Type", "application/xml").body(xml).expect().
		statusCode(404).post("/application");	
	}

	/*
	 * Make sure that creating the application with a Property's value without 
	 * a value doesn't work. e.g.
	 * <application name=[name]>
	 *	<property name="db.server" timeToLive="86400">
	 *		<value environment="dev">db.dev.company.corp</value>
	 *		<value environment="local"> </value>
	 *	</property> 
	 * </application>
	 */
	@Test
	public void shouldNotWorkWithoutPropertyValuesValue(){
		Map<String,String> values = new HashMap<String, String>();
		values.put(environments.get(0)[0], environments.get(0)[1]);
		values.put(environments.get(1)[0], " ");

		List<Property> properties = new ArrayList<Property>();
		properties.add(new Property("db.server","86400",values));
		String xml = Util.formatApplicationQuery(applicationName, properties);
		given().header("Content-Type", "application/xml").body(xml).expect().
		statusCode(404).post("/application");
	}

	@AfterClass
	public static void tearDownClass(){
		for(String[] environment: environments){
			String url = "/environment/" + environment[0];
			given().delete(url);
		}
	}
}