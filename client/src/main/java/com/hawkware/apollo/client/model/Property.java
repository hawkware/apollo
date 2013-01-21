package com.hawkware.apollo.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "property")
public class Property {
    private String name;

    private String value;

    private long timeToLive;

    public Property() {
    }

    @XmlAttribute(name = "name")
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

    @XmlAttribute(name = "timeToLive")
    public long getTimeToLive() {
	return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
	this.timeToLive = timeToLive;
    }

    @Override
    public String toString() {
	return "Property [name=" + name + ", value=" + value + ", timeToLive=" + timeToLive + "]";
    }

}
