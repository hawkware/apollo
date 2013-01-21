package com.hawkware.apollo.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "application")
public class ApplicationResource {

    private String name;

    private List<PropertyResource> properties = new ArrayList<PropertyResource>();

    private List<ContextResource> contexts = new ArrayList<ContextResource>();

    public ApplicationResource() {
    }

    public ApplicationResource(String name, List<PropertyResource> properties) {
	this.name = name;
	this.properties = properties;
    }

    @XmlElementWrapper(name = "properties")
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

    @XmlElementWrapper(name = "contexts")
    @XmlElements(value = { @XmlElement(name = "context", type = ContextResource.class) })
    public List<ContextResource> getContexts() {
	return contexts;
    }

    public void setContexts(List<ContextResource> contexts) {
	this.contexts = contexts;
    }

    @Override
    public String toString() {
	return "ApplicationResource [name=" + name + ", properties=" + properties + ", contexts=" + contexts + "]";
    }

}
