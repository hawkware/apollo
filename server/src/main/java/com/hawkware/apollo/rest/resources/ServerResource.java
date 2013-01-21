package com.hawkware.apollo.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class ServerResource {
    private List<HostNameResource> hostNames = new ArrayList<HostNameResource>();

    private List<IpAddressResource> ipAddresses = new ArrayList<IpAddressResource>();

    @XmlElements(value = { @XmlElement(name = "hostName", type = HostNameResource.class) })
    public List<HostNameResource> getHostNames() {
	return hostNames;
    }

    public void setHostNames(List<HostNameResource> hostNames) {
	this.hostNames = hostNames;
    }

    @XmlElements(value = { @XmlElement(name = "ipAddress", type = IpAddressResource.class) })
    public List<IpAddressResource> getIpAddresses() {
	return ipAddresses;
    }

    public void setIpAddresses(List<IpAddressResource> ipAddresses) {
	this.ipAddresses = ipAddresses;
    }

    @Override
    public String toString() {
	return "ServerResource [hostNames=" + hostNames + ", ipAddresses=" + ipAddresses + "]";
    }

}
