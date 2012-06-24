package com.hawkware.apollo.model;

import java.util.HashMap;
import java.util.Map;

public class Application {

    private Long id;

    private String name;

    private Map<String, Property> properties;

    public Application() {
    }

    public Application(String name) {
	this.name = name;
	this.properties = new HashMap<String, Property>();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Property getProperty(String name) {
	if (properties != null) {
	    return properties.get(name);
	}
	return null;
    }

    public Map<String, Property> getProperties() {
	return properties;
    }

    public void setProperties(Map<String, Property> properties) {
	this.properties = properties;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return "Application [id=" + id + ", name=" + name + ", properties=" + properties + "]";
    }

}
