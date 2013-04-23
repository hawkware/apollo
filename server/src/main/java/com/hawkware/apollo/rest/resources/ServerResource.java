package com.hawkware.apollo.rest.resources;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "server")
public class ServerResource {
	private String hostName;

	private String ipAddress;

	@XmlElement(name = "hostName")
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@XmlElement(name = "ipAddress")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "ServerResource [hostName=" + hostName + ", ipAddresses=" + ipAddress + "]";
	}

}
