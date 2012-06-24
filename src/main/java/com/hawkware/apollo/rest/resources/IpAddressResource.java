package com.hawkware.apollo.rest.resources;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "macAddress")
public class IpAddressResource {
    private String value;

    public IpAddressResource() {
    }

    public IpAddressResource(String value) {
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
