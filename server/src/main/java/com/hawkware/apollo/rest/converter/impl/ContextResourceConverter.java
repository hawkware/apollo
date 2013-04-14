package com.hawkware.apollo.rest.converter.impl;

import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.rest.converter.ResourceConverter;
import com.hawkware.apollo.rest.resources.EnvironmentResource;
import com.hawkware.apollo.rest.resources.ServerResource;

public class ContextResourceConverter extends ResourceConverter<EnvironmentResource, ApplicationContext> {

    ResourceConverter<ServerResource, Server> serverResourceConverter;

    @Override
    public ApplicationContext from(EnvironmentResource resource) {
	ApplicationContext appContext = new ApplicationContext();
	appContext.setName(resource.getName());
	appContext.setServers(serverResourceConverter.from(resource.getServerResources()));
	return appContext;
    }

    @Override
    public EnvironmentResource to(ApplicationContext appContext) {
	EnvironmentResource resource = new EnvironmentResource();
	resource.setName(appContext.getName());
	resource.setServerResources(serverResourceConverter.to(appContext.getServers()));
	return resource;
    }

    public void setServerResourceConverter(ResourceConverter<ServerResource, Server> serverResourceConverter) {
	this.serverResourceConverter = serverResourceConverter;
    }

}
