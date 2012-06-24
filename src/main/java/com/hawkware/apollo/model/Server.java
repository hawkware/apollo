package com.hawkware.apollo.model;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<String> hostNames;

    private List<String> ipAddresses;

    private List<String> macAddresses;

    public Server() {
	this.hostNames = new ArrayList<String>();
	this.ipAddresses = new ArrayList<String>();
	this.macAddresses = new ArrayList<String>();
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

    public List<String> getMacAddresses() {
	return macAddresses;
    }

    public void setMacAddresses(List<String> macAddresses) {
	this.macAddresses = macAddresses;
    }

    @Override
    public String toString() {
	return "Server [hostNames=" + hostNames + ", ipAddresses=" + ipAddresses + ", macAddresses=" + macAddresses
		+ "]";
    }

}
