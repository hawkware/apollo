package com.hawkware.apollo.model;


public class Server {
    private String hostName;

    private String ipAddress;

    public Server() {
    }

    public String getHostName() {
	return hostName;
    }

    public void setHostName(String hostName) {
	this.hostName = hostName;
    }

    public String getIpAddress() {
	return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
	return "Server [hostName=" + hostName + ", ipAddress=" + ipAddress + "]";
    }

}
