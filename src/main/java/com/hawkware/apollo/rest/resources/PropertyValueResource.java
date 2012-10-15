package com.hawkware.apollo.rest.resources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "value")
public class PropertyValueResource {
    private String context;

    private String value;

    public PropertyValueResource() {
    }

    public PropertyValueResource(String context, String value) {
	this.context = context;
	this.value = value;
    }

    @XmlAttribute(name = "context")
    public String getContext() {
	return context;
    }

    @XmlValue
    public String getValue() {
	return value;
    }

    @Override
    public String toString() {
	return "PropertyValueResource [context=" + context + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((context == null) ? 0 : context.hashCode());
	result = prime * result + ((value == null) ? 0 : value.hashCode());
	return result;
    }

    public void setContext(String context) {
	this.context = context;
    }

    public void setValue(String value) {
	this.value = value;
    }
}
