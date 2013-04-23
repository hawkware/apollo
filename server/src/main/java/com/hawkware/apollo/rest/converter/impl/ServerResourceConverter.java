package com.hawkware.apollo.rest.converter.impl;

import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.rest.converter.ResourceConverter;
import com.hawkware.apollo.rest.resources.ServerResource;

public class ServerResourceConverter extends ResourceConverter<ServerResource, Server> {

	@Override
	public ServerResource to(Server server) {
		ServerResource resource = new ServerResource();
		resource.setHostName(server.getHostName());
		resource.setIpAddress(server.getIpAddress());
		return resource;
	}

	@Override
	public Server from(ServerResource resource) {
		Server server = new Server();
		server.setHostName(resource.getHostName());
		server.setIpAddress(resource.getIpAddress());
		return server;
	}

}
