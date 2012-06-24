package com.hawkware.apollo.rest.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "property")
@XmlType(propOrder = { "name", "context", "value", "timeToLive" })
public class PropertyResource {
    private String name;

    private Object value;

    private String context;

    private long timeToLive;

    @XmlElement(name = "name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @XmlElement(name = "value")
    public String getValue() {
	return value != null ? value.toString() : null;
    }

    public void setValue(Object value) {
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
    public long getTimeToLive() {
	return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
	this.timeToLive = timeToLive;
    }

}
