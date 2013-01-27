package com.hawkware.apollo.model.builder.impl;

import java.util.ArrayList;
import java.util.List;

import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.model.builder.Builder;

public class ServerBuilder implements Builder<Server> {
    private List<String> hostNames;

    private List<String> ipAddresses;

    public ServerBuilder() {
	this.hostNames = new ArrayList<String>();
	this.ipAddresses = new ArrayList<String>();
    }

    @Override
    public Server build() {
	Server prop = new Server();
	prop.setHostNames(this.hostNames);
	prop.setIpAddresses(this.ipAddresses);
	return prop;
    }

    public ServerBuilder ipAddress(String ipAddress) {
	this.ipAddresses.add(ipAddress);
	return this;
    }

    public ServerBuilder hostName(String hostName) {
	this.hostNames.add(hostName);
	return this;
    }
}