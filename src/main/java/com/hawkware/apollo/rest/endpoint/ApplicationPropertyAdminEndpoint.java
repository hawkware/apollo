package com.hawkware.apollo.rest.endpoint;

import java.util.Collection;
import java.util.Map;

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
public class ApplicationPropertyAdminEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationPropertyAdminEndpoint.class);

    private PropertyResourceConverter propertyResourceConverter;

    @Autowired
    private ApplicationService applicationService;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getApplications() {

	Collection<Application> appl = applicationService.getApplications(null);
	logger.debug("applications=" + appl);

	// ApplicationsResource resource = null;

	return Response.ok().build();
    }

    @POST
    @Path("/{application}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getProperties(@PathParam("application") String application, ApplicationResource resource) {
	logger.debug("updating properties for application=" + application);

	Application appl = applicationService.getApplication(application);
	if (appl == null) {
	    logger.debug("no applicaiton found with name = " + application + " so creating new one");
	    appl = new Application(application);
	} else {
	    logger.debug("retrieved applicaiton " + appl);
	}

	Map<String, Property> properties = appl.getProperties();
	logger.debug("properties=" + properties);

	Collection<PropertyResource> propertyResources = resource.getPropertyResources();
	logger.debug("propertyResources to add for " + application + " = " + propertyResources);

	if (propertyResources != null) {
	    Collection<Property> props = propertyResourceConverter.to(propertyResources);
	    appl.addProperties(props);
	    applicationService.saveApplication(appl);
	    return Response.ok(
		    new ApplicationResource(appl.getName(), propertyResourceConverter.from(appl.getProperties()
			    .values()))).build();
	}
	return Response.ok("<message>application not updated!</message>").build();
    }

    @POST
    @Path("/{application}/context/{context}/property/{property}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getProperty(@PathParam("application") String application, @PathParam("context") String context,
	    @PathParam("property") String property, PropertyResource resource) {
	logger.debug("updating property=" + property + ", for application=" + application + ", context=" + context
		+ ", proeprty=" + property);

	Application appl = applicationService.getApplication(application);
	logger.debug("application=" + appl);
	Property currentProperty = appl.getProperty(property);
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

    public void setPropertyResourceConverter(PropertyResourceConverter propertyResourceConverter) {
	this.propertyResourceConverter = propertyResourceConverter;
    }
}
