package com.hawkware.apollo.rest.endpoint;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.rest.converter.impl.ServerResourceConverter;
import com.hawkware.apollo.rest.resources.ApplicationContextReqource;
import com.hawkware.apollo.rest.resources.ServerResource;
import com.hawkware.apollo.service.ContextService;

@Path("/context")
public class ApplicationContextEndpoint {

    @Context
    UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextEndpoint.class);

    private ServerResourceConverter serverResourceConverter;

    @Autowired
    private ContextService contextService;

    @GET
    @Path("/{context}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getServers(@PathParam("context") String context, @QueryParam("all") boolean allServers,
	    @Context HttpServletRequest requestContext, @Context SecurityContext secContext) {

	logger.debug("getting severs for context=" + context);

	Server server = getServer(requestContext);
	logger.debug("request came from sever=" + server);

	ApplicationContext ctx = contextService.getContext(context);
	logger.debug("retrieved context=" + ctx);

	if (ctx != null) {
	    List<ServerResource> serverResources = serverResourceConverter.from(ctx.getServers());
	    logger.debug("server resources =" + serverResources);
	    ApplicationContextReqource appCtxResource = new ApplicationContextReqource(ctx.getName(), serverResources);

	    logger.debug("returning appContext =" + appCtxResource);
	    return Response.ok(appCtxResource).build();
	} else {
	    logger.debug("context =" + context + " could not be found");
	    return Response.status(Status.NOT_FOUND).build();
	}

    }

    private Server getServer(HttpServletRequest requestContext) {
	Server server = new Server();
	server.setHostNames(Arrays.asList(requestContext.getRemoteHost()));
	server.setIpAddresses(Arrays.asList(requestContext.getRemoteAddr()));
	return server;
    }

    public void setServerResourceConverter(ServerResourceConverter serverResourceConverter) {
	this.serverResourceConverter = serverResourceConverter;
    }

}
