package com.hawkware.apollo.model;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<String> hostNames = new ArrayList<String>();

    private List<String> ipAddresses = new ArrayList<String>();

    public Server() {
	this.hostNames = new ArrayList<String>();
	this.ipAddresses = new ArrayList<String>();
    }

    public List<String> getHostNames() {
	return hostNames;
    }

    public void setHostNames(List<String> hostNames) {
	this.hostNames = hostNames;
    }

    public List<String> getIpAddresses() {
	return ipAddresses;
    }

    public void setIpAddresses(List<String> ipAddresses) {
	this.ipAddresses = ipAddresses;
    }

    @Override
    public String toString() {
	return "Server [hostNames=" + hostNames + ", ipAddresses=" + ipAddresses + "]";
    }

}
