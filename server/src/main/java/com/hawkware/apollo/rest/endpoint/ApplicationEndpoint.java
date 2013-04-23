package com.hawkware.apollo.rest.endpoint;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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

	private ApplicationService applicationService;

	private ContextValidator contextValidator;

	private boolean contextValidationEnabled = false;

	@GET
	@Path("/validate")
	public Response enableValidation() {
		contextValidationEnabled = !contextValidationEnabled;
		return Response.ok("" + contextValidationEnabled).build();
	}

	@GET
	@Path("/{application}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getApplication(@PathParam("application") String application,
			@HeaderParam("Environment") String environment, @Context HttpServletRequest requestContext,
			@Context SecurityContext secContext) {

		environment = validateContext(environment, requestContext);

		logger.debug("getting properties for application=" + application + ", environment=" + environment);

		Application appl = applicationService.getApplication(application);
		logger.debug("application=" + application);

		if (appl != null) {
			Collection<Property> properties = appl.getProperties();
			logger.debug("properties=" + properties);

			List<PropertyResource> resources = null;
			if (properties != null) {
				resources = propertyResourceConverter.from(properties, environment);
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
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getProperty(@PathParam("application") String application,
			@HeaderParam("Environment") String environment, @PathParam("property") String property,
			@Context HttpServletRequest requestContext, @Context SecurityContext secContext) {

		environment = validateContext(environment, requestContext);

		logger.debug("getting property=" + property + ", for application=" + application + ", environment="
				+ environment);
		System.out.println("getting property=" + property + ", for application=" + application + ", environment="
				+ environment);

		Application appl = applicationService.getApplication(application);

		System.out.println(appl);
		if (appl != null) {
			logger.debug("application=" + appl);
			Property prop = appl.getProperty(property);

			logger.debug("property=" + prop);
			System.out.println(prop);
			PropertyResource resource = null;
			if (prop != null) {
				resource = propertyResourceConverter.from(prop, environment);
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

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response addApplicartion(ApplicationResource resource) {
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
			logger.debug("no properties found for applicaiton=" + appl.getName());
		}
		applicationService.saveApplication(appl);
		return Response.ok(resource).build();
	}

	@POST
	@Path("/{application}/{environment}/property")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getProperty(@PathParam("application") String application,
			@PathParam("environment") String environment, PropertyResource resource) {
		logger.debug("updating property=" + resource.getName() + ", for application=" + application + ", environment="
				+ environment + ", proeprty=" + resource.getName());

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
	@Path("/{application}")
	public Response deleteApplication(@PathParam("application") String app) {
		Application application = applicationService.getApplication(app);
		applicationService.deleteApplication(application);
		return Response.ok().build();
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