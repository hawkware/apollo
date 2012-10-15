package com.hawkware.apollo.rest.endpoint;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.hawkware.apollo.exception.ContextValidationException;
import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.model.builder.impl.ApplicationBuilder;
import com.hawkware.apollo.model.builder.impl.PropertyBuilder;
import com.hawkware.apollo.rest.converter.impl.PropertyResourceConverter;
import com.hawkware.apollo.rest.resources.ApplicationResource;
import com.hawkware.apollo.rest.resources.PropertyResource;
import com.hawkware.apollo.service.ApplicationService;
import com.hawkware.apollo.validator.ContextValidator;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationEndpointTest {

    @Mock
    private ApplicationService applicationService;

    @Mock
    private ContextValidator contextValidator;

    private PropertyResourceConverter propertyResourceConverter = new PropertyResourceConverter();

    @Mock
    private HttpServletRequest requestContext;

    @InjectMocks
    private ApplicationEndpoint applicationEndpoint = new ApplicationEndpoint();

    @Before
    public void setUp() throws Exception {
	applicationEndpoint.setPropertyResourceConverter(propertyResourceConverter);
    }

    @Test(expected = WebApplicationException.class)
    public void testGetApplicationWithInvalidContext() throws ContextValidationException {
	String application = "app";
	String context = "dodgy";

	when(contextValidator.validateContext(context, requestContext)).thenThrow(
		new ContextValidationException("invalid context [" + context + "]"));

	applicationEndpoint.getApplication(application, context, requestContext, null);
    }

    @Test
    public void testGetApplicationWhenApplicationNull() throws ContextValidationException {

	String application = "app";
	String context = "dodgy";

	when(contextValidator.validateContext(context, requestContext)).thenReturn(context);
	when(applicationService.getApplication(application)).thenReturn(null);

	Response response = applicationEndpoint.getApplication(application, context, requestContext, null);

	assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	assertEquals("[application [" + application + "] could not be found]", response.getMetadata().get("Message")
		.toString());
    }

    @Test
    public void testGetApplicationWhenApplicationHasNoProperties() throws ContextValidationException {

	String application = "app";
	String context = "dev";
	Application appl = new ApplicationBuilder().name(application).build();

	when(contextValidator.validateContext(context, requestContext)).thenReturn("dev");
	when(applicationService.getApplication(application)).thenReturn(appl);

	Response response = applicationEndpoint.getApplication(application, context, requestContext, null);
	assertEquals(ApplicationResource.class, response.getEntity().getClass());

	ApplicationResource resource = (ApplicationResource) response.getEntity();
	assertEquals(application, resource.getName());

	assertEquals(0, resource.getProperties().size());
    }

    @Test
    public void testGetApplicationAllGood() throws ContextValidationException {

	String application = "app";
	String context = "dev";
	Application appl = new ApplicationBuilder().name(application)
		.property(new PropertyBuilder().name("test").value(context, "prop.value").build()).build();

	when(contextValidator.validateContext(context, requestContext)).thenReturn("dev");
	when(applicationService.getApplication(application)).thenReturn(appl);

	Response response = applicationEndpoint.getApplication(application, context, requestContext, null);
	assertEquals(ApplicationResource.class, response.getEntity().getClass());

	ApplicationResource resource = (ApplicationResource) response.getEntity();
	assertEquals(application, resource.getName());

	assertEquals(1, resource.getProperties().size());
    }

    @Test(expected = WebApplicationException.class)
    public void testGetPropertyNullProperty() throws ContextValidationException {

	String application = "app";
	String context = "dev";
	String property = "prop.test";
	String propertyValue = "prop.value";

	Application appl = new ApplicationBuilder().name(application).build();

	when(contextValidator.validateContext(context, requestContext)).thenReturn(context);
	when(applicationService.getApplication(application)).thenReturn(appl);

	Response response = applicationEndpoint.getProperty(application, context, property, requestContext, null);

    }

    @Test
    public void testGetPropertyOneProperty() throws ContextValidationException {

	String application = "app";
	String context = "dev";
	String property = "prop.test";
	String propertyValue = "prop.value";

	Application appl = new ApplicationBuilder().name(application)
		.property(new PropertyBuilder().name(property).value(context, propertyValue).build()).build();

	when(contextValidator.validateContext(context, requestContext)).thenReturn(context);
	when(applicationService.getApplication(application)).thenReturn(appl);

	Response response = applicationEndpoint.getProperty(application, context, property, requestContext, null);
	PropertyResource resource = (PropertyResource) response.getEntity();

	assertEquals(property, resource.getName());
	assertEquals(propertyValue, resource.getValue(context).getValue());
    }

}
