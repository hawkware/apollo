package com.hawkware.apollo.rest.endpoint;

import java.util.Collection;
import java.util.List;

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

    private boolean contextValidationEnabled = false;

    @GET
    @Path("/{application}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getApplication(@PathParam("application") String application,
	    @HeaderParam("context") String context, @Context HttpServletRequest requestContext,
	    @Context SecurityContext secContext) {

	context = validateContext(context, requestContext);

	logger.debug("getting properties for application=" + application + ", context=" + context);

	Application appl = applicationService.getApplication(application);
	logger.debug("application=" + application);

	if (appl != null) {
	    Collection<Property> properties = appl.getProperties();
	    logger.debug("properties=" + properties);

	    List<PropertyResource> resources = null;
	    if (properties != null) {
		resources = propertyResourceConverter.from(properties, context);
		logger.debug("propertyResources=" + resources);
	    }
	    ApplicationResource resourceWrapper = new ApplicationResource(application, resources);

	    return Response.ok(resourceWrapper).build();
	} else {
	    return Response.status(Status.NOT_FOUND)
		    .header("Message", "application [" + application + "] could not be found").build();
	}
    }

    @GET
    @Path("/{application}/property/{property:.*}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getProperty(@PathParam("application") String application, @HeaderParam("context") String context,
	    @PathParam("property") String property, @Context HttpServletRequest requestContext,
	    @Context SecurityContext secContext) {

	context = validateContext(context, requestContext);

	logger.debug("getting property=" + property + ", for application=" + application + ", context=" + context);
	System.out
		.println("getting property=" + property + ", for application=" + application + ", context=" + context);

	Application appl = applicationService.getApplication(application);

	System.out.println(appl);
	if (appl != null) {
	    logger.debug("application=" + appl);
	    Property prop = appl.getProperty(property);

	    logger.debug("property=" + prop);
	    System.out.println(prop);
	    PropertyResource resource = null;
	    if (prop != null) {
		resource = propertyResourceConverter.from(prop, context);
		System.out.println(resource);
		logger.debug("resource=" + resource);
	    } else {
		throw new WebApplicationException(Response.status(Status.NOT_FOUND)
			.header("Message", "property [" + property + "] could not be found").build());
	    }

	    logger.debug("returning resource [" + resource + "]");
	    return Response.ok(resource).build();
	} else {
	    throw new WebApplicationException(Response.status(Status.NOT_FOUND)
		    .header("Message", "application [" + application + "] could not be found").build());
	}
    }

    String validateContext(String context, HttpServletRequest requestContext) {
	if (contextValidationEnabled) {
	    try {
		context = contextValidator.validateContext(context, requestContext);
	    } catch (ContextValidationException cve) {
		logger.error("invalid context", cve);
		throw new WebApplicationException(Response.status(Status.UNAUTHORIZED)
			.header("Message", cve.getMessage()).build());
	    }
	}
	return context;
    }

    public void setPropertyResourceConverter(PropertyResourceConverter propertyResourceConverter) {
	this.propertyResourceConverter = propertyResourceConverter;
    }

    public void setApplicationService(ApplicationService applicationService) {
	this.applicationService = applicationService;
    }

    public void setContextValidator(ContextValidator contextValidator) {
	this.contextValidator = contextValidator;
    }

    public void setContextValidationEnabled(boolean contextValidationEnabled) {
	this.contextValidationEnabled = contextValidationEnabled;
    }
}
