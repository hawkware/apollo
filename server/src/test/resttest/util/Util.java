package util;

import com.jayway.restassured.RestAssured;

public class Util {
	
	public static void setUp(){
		RestAssured.baseURI = "http://0.0.0.0:8184";
	}
	
}