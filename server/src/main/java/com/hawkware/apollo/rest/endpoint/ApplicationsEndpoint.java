package com.hawkware.apollo.rest.endpoint;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawkware.apollo.exception.ContextValidationException;
import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.rest.converter.impl.ApplicationResourceConverter;
import com.hawkware.apollo.rest.resources.ApplicationResource;
import com.hawkware.apollo.rest.resources.ApplicationResources;
import com.hawkware.apollo.service.ApplicationService;
import com.hawkware.apollo.validator.ContextValidator;

@Path("/applications")
public class ApplicationsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationsEndpoint.class);

    private ApplicationResourceConverter applicationResourceConverter;

    private ApplicationService applicationService;

    private ContextValidator contextValidator;

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getApplications(@HeaderParam("Environment") String environment,
	    @Context HttpServletRequest requestContext, @Context SecurityContext secContext) {

	environment = validateContext(environment, requestContext);

	ApplicationResources resources = new ApplicationResources();
	Collection<Application> applications = applicationService.getApplications(null);
	logger.debug("applications=" + applications);
	if (applications != null) {
	    List<ApplicationResource> applicationResources = applicationResourceConverter.from(applications);
	    resources.setApplicationResources(applicationResources);
	}

	return Response.ok(resources).build();
    }

    String validateContext(String environment, HttpServletRequest requestContext) {
	try {
	    return contextValidator.validateContext(environment, requestContext);
	} catch (ContextValidationException cve) {
	    throw new WebApplicationException(Response.status(Status.UNAUTHORIZED)
		    .header("Message", "Access detected from invalid or unknown environment").build());
	}
    }

    public void setApplicationService(ApplicationService applicationService) {
	this.applicationService = applicationService;
    }

    public void setContextValidator(ContextValidator contextValidator) {
	this.contextValidator = contextValidator;
    }

    public void setApplicationResourceConverter(ApplicationResourceConverter applicationResourceConverter) {
	this.applicationResourceConverter = applicationResourceConverter;
    }
}
