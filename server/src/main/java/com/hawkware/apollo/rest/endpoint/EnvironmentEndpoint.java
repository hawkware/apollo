package com.hawkware.apollo.rest.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.rest.converter.ResourceConverter;
import com.hawkware.apollo.rest.resources.EnvironmentResource;
import com.hawkware.apollo.rest.resources.ServerResource;
import com.hawkware.apollo.service.ContextService;

@Path("/environment")
public class EnvironmentEndpoint {

	private static final Logger logger = LoggerFactory.getLogger(EnvironmentEndpoint.class);

	private ResourceConverter<EnvironmentResource, ApplicationContext> contextResourceConverter;

	private ContextService contextService;

	private ResourceConverter<ServerResource, Server> serverResourceConverter;

	@GET
	@Path("/{context}")
	public Response getContext(@PathParam("context") String context) {
		logger.debug("getting context =" + context);
		ApplicationContext applicationContext = contextService.getContext(context);

		if (applicationContext != null) {
			EnvironmentResource environmentResource = contextResourceConverter.to(applicationContext);
			logger.debug("converted context to resource [" + environmentResource + "]");
			return Response.ok(environmentResource).build();
		} else {
			logger.debug("couldnt find context [" + context + "] returning 404");
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.header("Message", "context [" + context + "] could not be found").build());
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response saveContexts(EnvironmentResource environmentResource) {
		logger.debug("saving context =" + environmentResource);
		ApplicationContext applicationContext = contextResourceConverter.from(environmentResource);

		if (applicationContext != null) {
			logger.debug("converted context to [" + applicationContext + "] saving... ");
			contextService.saveContext(applicationContext);
			return Response.ok(environmentResource).build();
		} else {
			logger.debug("couldnt convert context [" + environmentResource + "] returning BAD REQUEST");
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST)
					.header("Message", "context could not be updated").build());
		}
	}

	@DELETE
	@Path("/{context}")
	public Response deleteContext(@PathParam("context") String context) {
		logger.debug("deleting context =" + context);
		ApplicationContext applicationContext = contextService.getContext(context);

		if (applicationContext != null) {
			contextService.deleteContext(applicationContext);
			logger.debug(" context =" + context + " deleted");
			return Response.ok().build();
		} else {
			logger.debug("couldnt find context [" + context + "] returning 404");
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.header("Message", "context [" + context + "] could not be found").build());
		}
	}

	@POST
	@Path("/{context}/server")
	@Consumes(MediaType.APPLICATION_XML)
	public Response saveServer(@PathParam("context") String context, ServerResource serverResource) {

		logger.debug("adding server [" + serverResource + "] to context [" + context + "]");
		ApplicationContext applicationContext = contextService.getContext(context);

		if (applicationContext != null) {
			Server server = serverResourceConverter.from(serverResource);
			applicationContext.addServer(server);
			contextService.saveContext(applicationContext);
			logger.debug(" server [" + server + "] has been added to context [" + context + "]");
			return Response.ok().build();
		} else {
			logger.debug("couldnt find context [" + context + "] returning 404");
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.header("Message", "context [" + context + "] not be found").build());
		}
	}

	@DELETE
	@Path("/{context}/server/ipaddress/{ipAddress}")
	public Response deleteServerByIpAddress(@PathParam("context") String context,
			@PathParam("ipAddress") String ipAddress) {

		logger.debug("removing server with ipAddress [" + ipAddress + "] from context [" + context + "]");
		ApplicationContext applicationContext = contextService.getContext(context);

		if (applicationContext != null) {
			List<Server> toDelete = new ArrayList<Server>();

			for (Server server : applicationContext.getServers()) {
				if (ipAddress.equals(server.getIpAddress())) {
					toDelete.add(server);
				}
			}

			logger.debug("removing the following servers [" + toDelete + "] from context [" + context + "]");
			applicationContext.getServers().removeAll(toDelete);
			contextService.saveContext(applicationContext);
			return Response.ok().build();
		} else {
			logger.debug("couldnt find context [" + context + "] returning 404");
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.header("Message", "context [" + context + "] not be found").build());
		}

	}

	@DELETE
	@Path("/{context}/server/hostname/{hostname}")
	public Response deleteServerByHostName(@PathParam("context") String context, @PathParam("hostname") String hostname) {

		logger.debug("removing server with hostname [" + hostname + "] from context [" + context + "]");
		ApplicationContext applicationContext = contextService.getContext(context);

		if (applicationContext != null) {
			List<Server> toDelete = new ArrayList<Server>();

			for (Server server : applicationContext.getServers()) {
				if (hostname.equals(server.getHostName())) {
					toDelete.add(server);
				}
			}

			logger.debug("removing the following servers [" + toDelete + "] from context [" + context + "]");
			applicationContext.getServers().removeAll(toDelete);
			contextService.saveContext(applicationContext);
			return Response.ok().build();
		} else {
			logger.debug("couldnt find context [" + context + "] returning 404");
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.header("Message", "context [" + context + "] not be found").build());
		}
	}

	public void setContextResourceConverter(
			ResourceConverter<EnvironmentResource, ApplicationContext> contextResourceConverter) {
		this.contextResourceConverter = contextResourceConverter;
	}

	public void setContextService(ContextService contextService) {
		this.contextService = contextService;
	}

	public void setServerResourceConverter(ResourceConverter<ServerResource, Server> serverResourceConverter) {
		this.serverResourceConverter = serverResourceConverter;
	}

}