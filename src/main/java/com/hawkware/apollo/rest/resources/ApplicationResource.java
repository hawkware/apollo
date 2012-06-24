package com.hawkware.apollo.rest.resources;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "application")
public class ApplicationResource {

    private String name;

    private String context;

    private List<PropertyResource> propertyResources;

    public ApplicationResource() {
    }

    public ApplicationResource(String name, String context) {
	this.name = name;
	this.context = context;
    }

    @XmlElements(value = { @XmlElement(name = "property", type = PropertyResource.class) })
    public List<PropertyResource> getPropertyResources() {
	return propertyResources;
    }

    public void setPropertyResources(List<PropertyResource> propertyResources) {
	this.propertyResources = propertyResources;
    }

    @XmlAttribute(name = "name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getContext() {
	return context;
    }

    @XmlAttribute(name = "context")
    public void setContext(String context) {
	this.context = context;
    }

}
