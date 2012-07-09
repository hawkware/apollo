package com.hawkware.apollo.rest.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "property")
@XmlType(propOrder = { "name", "context", "value", "timeToLive" })
public class PropertyResource {
    private String name;

    private String value;

    private String context;

    private Long timeToLive;

    public PropertyResource() {
    }

    public PropertyResource(String name, String context, String value, long timeToLive) {
	this.name = name;
	this.context = context;
	this.value = value;
	this.timeToLive = timeToLive;
    }

    @XmlElement(name = "name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @XmlElement(name = "value")
    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    @XmlElement(name = "context")
    public String getContext() {
	return context;
    }

    public void setContext(String context) {
	this.context = context;
    }

    @XmlElement(name = "timeToLive")
    public Long getTimeToLive() {
	return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
	this.timeToLive = timeToLive;
    }

    @Override
    public String toString() {
	return "PropertyResource [name=" + name + ", value=" + value + ", context=" + context + ", timeToLive="
		+ timeToLive + "]";
    }

}
