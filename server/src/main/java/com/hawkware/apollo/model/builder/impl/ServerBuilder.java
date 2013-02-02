package com.hawkware.apollo.model.builder.impl;

import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.model.builder.Builder;

public class ServerBuilder implements Builder<Server> {
    private String hostName;

    private String ipAddress;

    public ServerBuilder() {
	this.hostName = "";
	this.ipAddress = "";
    }

    @Override
    public Server build() {
	Server prop = new Server();
	prop.setHostName(this.hostName);
	prop.setIpAddress(this.ipAddress);
	return prop;
    }

    public ServerBuilder ipAddress(String ipAddress) {
	this.ipAddress = ipAddress;
	return this;
    }

    public ServerBuilder hostName(String hostName) {
	this.hostName = hostName;
	return this;
    }
}
