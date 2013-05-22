package com.hawkware.apollo.resttest.Util;
import java.util.*;

public class Property {
	public String name;
	public String timeToLive;
	//The key is the name of the environment and the values is the value assigned
	//to it
	public Map<String,String> values;
	
	public Property(String name, String timeToLive, Map<String,String> values){
		this.name = name;
		this.timeToLive = timeToLive;
		this.values = values;
	}
}