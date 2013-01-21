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

    public void setContext(String context) {
	this.context = context;
    }

    public void setValue(String value) {
	this.value = value;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((context == null) ? 0 : context.hashCode());
	result = prime * result + ((value == null) ? 0 : value.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PropertyValueResource other = (PropertyValueResource) obj;
	if (context == null) {
	    if (other.context != null)
		return false;
	} else if (!context.equals(other.context))
	    return false;
	if (value == null) {
	    if (other.value != null)
		return false;
	} else if (!value.equals(other.value))
	    return false;
	return true;
    }

}
