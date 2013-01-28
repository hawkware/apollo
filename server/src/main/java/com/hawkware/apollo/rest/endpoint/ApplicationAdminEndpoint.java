package com.hawkware.apollo.rest.endpoint;

import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.model.Property;
import com.hawkware.apollo.rest.converter.impl.PropertyResourceConverter;
import com.hawkware.apollo.rest.resources.ApplicationResource;
import com.hawkware.apollo.rest.resources.PropertyResource;
import com.hawkware.apollo.service.ApplicationService;

@Path("/admin/application")
public class ApplicationAdminEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationAdminEndpoint.class);

    private PropertyResourceConverter propertyResourceConverter;

    @Autowired
    private ApplicationService applicationService;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getApplications() {

	Collection<Application> appl = applicationService.getApplications(null);
	logger.debug("applications=" + appl);

	return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public Response getProperties(ApplicationResource resource) {
	logger.debug("adding  resource=" + resource);

	Application appl = applicationService.getApplication(resource.getName());
	if (appl == null) {
	    logger.debug("no applicaiton found with name = " + resource.getName() + " so creating new one");
	    appl = new Application(resource.getName());
	} else {
	    logger.debug("retrieved applicaiton " + appl);
	}

	Collection<Property> properties = appl.getProperties();
	logger.debug("properties=" + properties);

	Collection<PropertyResource> propertyResources = resource.getProperties();
	logger.debug("propertyResources to add for " + resource.getName() + " = " + propertyResources);

	if (propertyResources != null) {
	    Collection<Property> props = propertyResourceConverter.to(propertyResources);
	    appl.addProperties(props);
	} else {
	    logger.debug("no properties foudn for applicaiton=" + appl.getName());
	}
	applicationService.saveApplication(appl);
	return Response.ok(
		new ApplicationResource(appl.getName(), propertyResourceConverter.from(appl.getProperties()))).build();
    }

    @POST
    @Path("/{application}/{context}/property")
    @Produces(MediaType.APPLICATION_XML)
    public Response getProperty(@PathParam("application") String application, @PathParam("context") String context,
	    PropertyResource resource) {
	logger.debug("updating property=" + resource.getName() + ", for application=" + application + ", context="
		+ context + ", proeprty=" + resource.getName());

	Application appl = applicationService.getApplication(application);
	logger.debug("application=" + appl);
	Property currentProperty = appl.getProperty(resource.getName());
	Property updatedProperty = propertyResourceConverter.to(resource);
	logger.debug("property=" + currentProperty);

	if (currentProperty != null) {
	    logger.debug("updating currentProperty=" + currentProperty + ", with updatedProperty=" + updatedProperty);
	    currentProperty.getValuesByContext().putAll(updatedProperty.getValuesByContext());
	} else {
	    logger.debug("adding updatedProperty=" + updatedProperty + " as it doesnt already exist");
	    appl.addProperty(updatedProperty);
	}
	applicationService.saveApplication(appl);
	return Response.ok().build();

    }

    @DELETE
    @Path("/{application}/{context}/{property}")
    public Response deleteProperty(@PathParam("application") String application, @PathParam("context") String context,
	    @PathParam("property") String property) {
	Application appl = applicationService.getApplication(application);
	applicationService.deleteApplication(appl);

	return Response.noContent().build();
    }

    @DELETE
    @Path("/{application}")
    public Response deleteApplication(@PathParam("application") String app) {
	Application application = applicationService.getApplication(app);
	applicationService.deleteApplication(application);
	return Response.noContent().build();
    }

    public void setPropertyResourceConverter(PropertyResourceConverter propertyResourceConverter) {
	this.propertyResourceConverter = propertyResourceConverter;
    }
}
