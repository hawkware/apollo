package com.hawkware.apollo.rest.resources;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "value")
public class PropertyValueResource {
    private String environment;

	private String value;

	public PropertyValueResource() {
	}

    public PropertyValueResource(String environment, String value) {
	this.environment = environment;
	this.value = value;
    }

    @XmlAttribute(name = "environment")
    public String getEnvironment() {
	return environment;
    }

	@XmlValue
	public String getValue() {
		return value;
	}

    @Override
    public String toString() {
	return "PropertyValueResource [environment=" + environment + ", value=" + value + "]";
    }

    public void setEnvironment(String environment) {
	this.environment = environment;
    }

	public void setValue(String value) {
		this.value = value;
	}

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((environment == null) ? 0 : environment.hashCode());
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
	if (environment == null) {
	    if (other.environment != null)
		return false;
	} else if (!environment.equals(other.environment))
	    return false;
	if (value == null) {
	    if (other.value != null)
		return false;
	} else if (!value.equals(other.value))
	    return false;
	return true;
    }

}
