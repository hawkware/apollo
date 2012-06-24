package com.hawkware.apollo.rest.converter.impl;

import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.rest.converter.ResourceConverter;
import com.hawkware.apollo.rest.resources.HostNameResource;
import com.hawkware.apollo.rest.resources.IpAddressResource;
import com.hawkware.apollo.rest.resources.MacAddressResource;
import com.hawkware.apollo.rest.resources.ServerResource;

public class ServerResourceConverter extends ResourceConverter<Server, ServerResource> {

    @Override
    public ServerResource from(Server server) {
	ServerResource resource = new ServerResource();
	for (String hostName : server.getHostNames()) {
	    resource.getHostNames().add(new HostNameResource(hostName));
	}

	for (String ipAddress : server.getIpAddresses()) {
	    resource.getIpAddresses().add(new IpAddressResource(ipAddress));
	}

	for (String macAddress : server.getMacAddresses()) {
	    resource.getMacAddresses().add(new MacAddressResource(macAddress));
	}
	return resource;
    }

    @Override
    public Server to(ServerResource resource) {
	Server server = new Server();
	for (HostNameResource hostName : resource.getHostNames()) {
	    server.getHostNames().add(hostName.getValue());
	}

	for (IpAddressResource ipAddress : resource.getIpAddresses()) {
	    server.getIpAddresses().add(ipAddress.getValue());
	}

	for (MacAddressResource macAddress : resource.getMacAddresses()) {
	    server.getMacAddresses().add(macAddress.getValue());
	}
	return server;
    }

}
