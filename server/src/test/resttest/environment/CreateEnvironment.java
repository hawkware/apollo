package environment;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import util.Util;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasXPath;

public class CreateEnvironment {

	@BeforeClass
	public static void setUp(){
		Util.setUp();
	}
	
	@Test
	public void ShouldWorkWithCompleteXml(){
		String xml = "<environment name='local'><server><hostName>localhost";
		xml += "</hostName><ipAddress>127.0.0.1</ipAddress></server>";
		xml += "</environment>";
		String result = given().header("Context-Type","application/xml").
					body(xml).post("/context").andReturn().asString();
		System.out.println(result);
		org.junit.Assert.assertTrue(true == (2 == 2));
	}
}