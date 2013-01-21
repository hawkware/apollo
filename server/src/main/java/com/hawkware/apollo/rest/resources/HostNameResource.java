package com.hawkware.apollo.rest.resources;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "hostName")
public class HostNameResource {
    private String value;

    public HostNameResource() {
    }

    public HostNameResource(String value) {
	this.value = value;
    }

    @XmlValue
    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

}
