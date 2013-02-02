package com.hawkware.apollo.rest.converter.impl;

import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.rest.converter.ResourceConverter;
import com.hawkware.apollo.rest.resources.ServerResource;

public class ServerResourceConverter extends ResourceConverter<Server, ServerResource> {

    @Override
    public ServerResource from(Server server) {
	ServerResource resource = new ServerResource();
	resource.setHostName(server.getHostName());
	resource.setIpAddress(server.getIpAddress());
	return resource;
    }

    @Override
    public Server to(ServerResource resource) {
	Server server = new Server();
	server.setHostName(resource.getHostName());
	server.setIpAddress(resource.getIpAddress());
	return server;
    }

}
