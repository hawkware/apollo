package com.hawkware.apollo.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "application")
public class ApplicationResource {

    private String name;

    private List<PropertyResource> properties = new ArrayList<PropertyResource>();

    public ApplicationResource() {
    }

    public ApplicationResource(String name, List<PropertyResource> properties) {
	this.name = name;
	this.properties = properties;
    }

    @XmlElements(value = { @XmlElement(name = "property", type = PropertyResource.class) })
    public List<PropertyResource> getProperties() {
	return properties;
    }

    public void setProperties(List<PropertyResource> properties) {
	this.properties = properties;
    }

    @XmlAttribute(name = "name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return "ApplicationResource [name=" + name + ", properties=" + properties + "]";
    }

}
