package com.hawkware.apollo.rest.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private Map<String, PropertyValueResource> valuesMap = new HashMap<String, PropertyValueResource>();

    private Long timeToLive;

    public PropertyResource() {
	this.valuesMap = new HashMap<String, PropertyValueResource>();
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
    public List<PropertyValueResource> getValues() {
	return new ArrayList<PropertyValueResource>(valuesMap.values());
    }

    @XmlTransient
    public Map<String, PropertyValueResource> getValuesMap() {
	return valuesMap;
    }

    public PropertyValueResource getValue(String context) {
	return valuesMap.get(context);
    }

    public void setValuesMap(Map<String, PropertyValueResource> values) {
	this.valuesMap = values;
    }

    public void addValue(PropertyValueResource value) {
	valuesMap.put(value.getContext(), value);
    }

    @XmlAttribute(name = "timeToLive")
    public Long getTimeToLive() {
	return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
	this.timeToLive = timeToLive;
    }

    public void setValues(List<PropertyValueResource> values) {
	if (values != null) {
	    for (PropertyValueResource value : values) {
		valuesMap.put(value.getContext(), value);
	    }
	}
    }

    @Override
    public String toString() {
	return "PropertyResource [name=" + name + ", valuesMap=" + valuesMap + ", timeToLive=" + timeToLive + "]";
    }

}
