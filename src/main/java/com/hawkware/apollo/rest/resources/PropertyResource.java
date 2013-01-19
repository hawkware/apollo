package com.hawkware.apollo.rest.resources;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "property")
@XmlType(propOrder = { "name", "values", "timeToLive" })
public class PropertyResource {
    private String name;

    private Map<String, PropertyValueResource> values;

    private Long timeToLive;

    public PropertyResource() {
	this.values = new HashMap<String, PropertyValueResource>();
    }

    public PropertyResource(String name, long timeToLive) {
	this();
	this.name = name;
	this.timeToLive = timeToLive;
    }

    @XmlAttribute(name = "name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @XmlElement(name = "value")
    public Collection<PropertyValueResource> getValues() {
	return values.values();
    }

    @XmlTransient
    public Map<String, PropertyValueResource> getValuesMap() {
	return values;
    }

    public PropertyValueResource getValue(String context) {
	return values.get(context);
    }

    public void setValuesMap(Map<String, PropertyValueResource> values) {
	this.values = values;
    }

    public void addValue(PropertyValueResource value) {
	values.put(value.getContext(), value);
    }

    @XmlAttribute(name = "timeToLive")
    public Long getTimeToLive() {
	return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
	this.timeToLive = timeToLive;
    }

    @Override
    public String toString() {
	return "PropertyResource [name=" + name + ", values=" + values + ", timeToLive=" + timeToLive + "]";
    }

}
