package com.hawkware.apollo.rest.endpoint;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hawkware.apollo.exception.ContextValidationException;
import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.model.Property;
import com.hawkware.apollo.rest.converter.impl.PropertyResourceConverter;
import com.hawkware.apollo.rest.resources.ApplicationResource;
import com.hawkware.apollo.rest.resources.PropertyResource;
import com.hawkware.apollo.service.ApplicationService;
import com.hawkware.apollo.validator.ContextValidator;

@Path("/application")
public class ApplicationEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationEndpoint.class);

    private PropertyResourceConverter propertyResourceConverter;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ContextValidator contextValidator;

    @GET
    @Path("/{application}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getProperties(@PathParam("application") String application, @HeaderParam("context") String context,
	    @Context HttpServletRequest requestContext, @Context SecurityContext secContext) {

	try {
	    context = contextValidator.validateContext(context, requestContext);
	} catch (ContextValidationException cve) {
	    logger.error("invalid context", cve);
	    throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).header("Message", cve.getMessage())
		    .build());
	}

	logger.debug("getting properties for application=" + application + ", context=" + context);

	Application appl = applicationService.getApplication(application);
	logger.debug("application=" + application);

	Map<String, Property> properties = appl.getProperties();
	logger.debug("properties=" + properties);

	List<PropertyResource> resources = null;
	if (properties != null) {
	    resources = propertyResourceConverter.from(properties.values(), context);
	    logger.debug("propertyResources=" + resources);
	}
	ApplicationResource resourceWrapper = new ApplicationResource(application, resources);

	return Response.ok(resourceWrapper).build();
    }

    @GET
    @Path("/{application}/property/{property}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getProperty(@PathParam("application") String application, @HeaderParam("context") String context,
	    @PathParam("property") String property, @Context HttpServletRequest requestContext,
	    @Context SecurityContext secContext) {

	try {
	    context = contextValidator.validateContext(context, requestContext);
	} catch (ContextValidationException cve) {
	    logger.error("invalid context", cve);
	    throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).header("Message", cve.getMessage())
		    .build());
	}

	logger.debug("getting property=" + property + ", for application=" + application + ", context=" + context);

	Application appl = applicationService.getApplication(application);

	logger.debug("application=" + appl);
	Property prop = appl.getProperty(property);

	logger.debug("property=" + prop);

	PropertyResource resource = null;
	if (prop != null) {
	    resource = propertyResourceConverter.from(prop, context);
	    logger.debug("resource=" + resource);
	}

	if (resource != null) {
	    logger.debug("returning resource");
	    return Response.ok(resource).build();
	} else {
	    logger.debug("returning 404");
	    return Response.status(Status.NOT_FOUND).build();
	}
    }

    public void setPropertyResourceConverter(PropertyResourceConverter propertyResourceConverter) {
	this.propertyResourceConverter = propertyResourceConverter;
    }
}
