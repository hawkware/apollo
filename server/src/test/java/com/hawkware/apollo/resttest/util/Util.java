package com.hawkware.apollo.resttest.util;

import com.jayway.restassured.RestAssured;

public class Util {
	
	public static void setUp(){
		RestAssured.baseURI = "http://0.0.0.0:8080/apollo";
	}
	
}