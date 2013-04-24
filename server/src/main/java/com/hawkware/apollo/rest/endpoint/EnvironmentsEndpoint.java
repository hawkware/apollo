package com.hawkware.apollo.rest.endpoint;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.rest.converter.ResourceConverter;
import com.hawkware.apollo.rest.resources.EnvironmentResource;
import com.hawkware.apollo.rest.resources.EnvironmentResources;
import com.hawkware.apollo.service.ContextService;

@Path("/environments")
public class EnvironmentsEndpoint {

	private static final Logger logger = LoggerFactory.getLogger(EnvironmentsEndpoint.class);

	private ResourceConverter<EnvironmentResource, ApplicationContext> contextResourceConverter;

	private ContextService contextService;

	@GET
	public Response getEnvironments() {
		logger.debug("getting enviromnent list");

		Collection<ApplicationContext> applicationContext = contextService.getContexts(new HashMap<String, Object>());
		List<EnvironmentResource> environmentResourceList = contextResourceConverter.to(applicationContext);
		EnvironmentResources environmentResources = new EnvironmentResources(environmentResourceList);

		logger.debug("environmentResources = " + environmentResources);
		return Response.ok(environmentResources).build();
	}

	public void setContextResourceConverter(
			ResourceConverter<EnvironmentResource, ApplicationContext> contextResourceConverter) {
		this.contextResourceConverter = contextResourceConverter;
	}

	public void setContextService(ContextService contextService) {
		this.contextService = contextService;
	}

}
