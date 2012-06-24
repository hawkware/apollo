package com.hawkware.apollo.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

public class ServerResource {
    private List<HostNameResource> hostNames = new ArrayList<HostNameResource>();

    private List<IpAddressResource> ipAddresses = new ArrayList<IpAddressResource>();

    private List<MacAddressResource> macAddresses = new ArrayList<MacAddressResource>();

    @XmlElementWrapper(name = "hostNames")
    @XmlElements(value = { @XmlElement(name = "hostName", type = HostNameResource.class) })
    public List<HostNameResource> getHostNames() {
	return hostNames;
    }

    public void setHostNames(List<HostNameResource> hostNames) {
	this.hostNames = hostNames;
    }

    @XmlElementWrapper(name = "ipAddresses")
    @XmlElements(value = { @XmlElement(name = "ipAddress", type = IpAddressResource.class) })
    public List<IpAddressResource> getIpAddresses() {
	return ipAddresses;
    }

    public void setIpAddresses(List<IpAddressResource> ipAddresses) {
	this.ipAddresses = ipAddresses;
    }

    @XmlElementWrapper(name = "macAddresses")
    @XmlElements(value = { @XmlElement(name = "macAddress", type = MacAddressResource.class) })
    public List<MacAddressResource> getMacAddresses() {
	return macAddresses;
    }

    public void setMacAddresses(List<MacAddressResource> macAddresses) {
	this.macAddresses = macAddresses;
    }

    @Override
    public String toString() {
	return "ServerResource [hostNames=" + hostNames + ", ipAddresses=" + ipAddresses + ", macAddresses="
		+ macAddresses + "]";
    }

}
