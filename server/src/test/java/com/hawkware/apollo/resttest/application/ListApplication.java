package com.hawkware.apollo.resttest.application;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.xml.XmlPath.with;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import java.util.*;

public class ListApplication {
	
	private static List<String[]> environments;
	private static Map<String,List<Property>> applications;
	private static String[] appNames = {"sample-app-on", "sample-app-tw"};

	private boolean isPropertyInXmlString(String xmlString, 
			Property propertyToCheckFor){
		boolean allFound = true;
		//check if the expected property name and timeToLive an in xml string
		allFound = xmlString.contains(propertyToCheckFor.name);
		allFound = xmlString.contains(propertyToCheckFor.timeToLive) && allFound;
		
		//check if the values in the property an in the xml string
		Set<String> valueSet = propertyToCheckFor.values.keySet();
		Map<String,String> values = propertyToCheckFor.values;
	
		for(String key: valueSet){
			allFound = xmlString.contains(values.get(key)) && allFound;
			allFound = xmlString.contains(key) && allFound;
		}
		return allFound;
	}
	
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
	public void ShouldReturnAllApplications(){
		boolean allfound = true;
		Set<String> keys = applications.keySet();
		String result = given().
								header("Content-Type","application/xml").
								get("/applications").
								andReturn().
						asString();
		for(String key: keys){
			int count = with(result).getInt("applications.application." +
					"find{it.name='"+key+"'}.size()");
			allfound = (count == 1) && allfound;
		}
		Assert.assertTrue(allfound);
	}
	
	@Test
	public void shouldReturnNamedApplication(){
		boolean allFound = true;
		String appName = appNames[new Random().nextInt(appNames.length)];
		String xml = given().
							header("Content-Type","application/xml").
							get("/application/"+appName).
							andReturn().
					asString();
		//check if the application name returned is the one passed to the 
		//endpoint
		allFound = xml.contains(appName);
		for(Property currentProperty: applications.get(appName)){
			allFound = isPropertyInXmlString(xml, currentProperty);
		}
		Assert.assertTrue(allFound);
	}

	@Test
	public void shouldfailForNonExistingApplication(){
		given().
				header("Content-Type","application/xml").
				expect().
				statusCode(404).
		get("/application/dummy");
	}
	
	@Test
	public void shouldReturnNamedApplicationProperties(){
		String appName = appNames[new Random().nextInt(appNames.length)];
		boolean allFound = true;
		String xml = given().
							header("Content-Type","application/xml").
							get("/application/"+ appName+"/property").
					 andReturn().asString();
		List<Property> properties = applications.get(appName);
		for(Property prop: properties){
			allFound = isPropertyInXmlString(xml,prop);
		}
		Assert.assertTrue(allFound);
	}
	
	@Test
	public void shouldReturnSpecifiedProperty(){
		Random rand = new Random();
		String selectedAppName = appNames[rand.nextInt(appNames.length)];
		List<Property> appProperties = applications.get(selectedAppName);
		Property selectedProperty = appProperties.get(
				rand.nextInt(appProperties.size()));
		String url = String.format(
									"/application/%s/property/%s",
									selectedAppName,
									selectedProperty.name
									); 
		String xml = given().
							header("Content-Type","application/xml").
							get(url).
							andReturn().
					 asString();
		Assert.assertTrue(isPropertyInXmlString(xml,selectedProperty));
	}
	
	@Test
	public void shouldfailWithNonExistingApplicationProperty(){
		Random rand = new Random();
		String selectedAppName = appNames[rand.nextInt(appNames.length)];
		String dummyPropertyName = "dummy";
		String url = String.format(
									"/application/%s/property/%s",
									selectedAppName,
									dummyPropertyName
									);
		given().
				header("Content-Type","application/xml").
				expect().
				statusCode(404).
		get(url);
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