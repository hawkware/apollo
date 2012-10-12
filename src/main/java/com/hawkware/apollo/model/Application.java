package com.hawkware.apollo.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Application {

    private Long id;

    private String name;

    private Map<String, Property> properties = new HashMap<String, Property>();

    private Map<String, ApplicationContext> contexts = new HashMap<String, ApplicationContext>();

    public Application() {
    }

    public Application(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void addProperties(Collection<Property> props) {
	for (Property prop : props) {
	    addProperty(prop);
	}
    }

    public void addProperty(Property property) {

	Property propToUpdate = properties.get(property.getName());
	if (propToUpdate != null) {
	    if (propToUpdate.getValuesByContext() != null) {
		propToUpdate.getValuesByContext().putAll(property.getValuesByContext());
	    } else {
		propToUpdate.setValuesByContext(property.getValuesByContext());
	    }
	} else {
	    properties.put(property.getName(), property);
	}

    }

    public Property getProperty(String name) {
	return properties.get(name);
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

    public Map<String, ApplicationContext> getContexts() {
	return contexts;
    }

    public void setContexts(Map<String, ApplicationContext> contexts) {
	this.contexts = contexts;
    }

    @Override
    public String toString() {
	return "Application [id=" + id + ", name=" + name + ", properties=" + properties + ", contexts=" + contexts
		+ "]";
    }

}
