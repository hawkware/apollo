package com.hawkware.apollo.client.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.hawkware.apollo.client.PropertyService;

public class ApolloPropertyPlaceholderConfigurerTest {

    private ApolloPropertyPlaceholderConfigurer apolloPropertyPlaceholderConfigurer;

    @Before
    public void setUp() throws Exception {
	apolloPropertyPlaceholderConfigurer = new ApolloPropertyPlaceholderConfigurer();
        PropertyService propertyService = Mockito.mock(PropertyService.class);
	apolloPropertyPlaceholderConfigurer.setPropertyService(propertyService);
    }

    @Test
    public void testGetPropertyService() {
	String expectedApplication = "apollo";
	String expectedServerUrl = "http://localhost:8140/apollo";
	String expectedEnvironment = "dev";

	apolloPropertyPlaceholderConfigurer = new ApolloPropertyPlaceholderConfigurer();
	apolloPropertyPlaceholderConfigurer.setApplication(expectedApplication);
	apolloPropertyPlaceholderConfigurer.setServerUrl(expectedServerUrl);
	apolloPropertyPlaceholderConfigurer.setEnvironment(expectedEnvironment);

	PropertyService propertyService = apolloPropertyPlaceholderConfigurer.getPropertyService();

	assertNotNull(propertyService);
	assertEquals(expectedApplication, propertyService.getApplication());
	assertEquals(expectedServerUrl, propertyService.getServerUrl());
	assertEquals(expectedEnvironment, propertyService.getEnvironment());
    }
}