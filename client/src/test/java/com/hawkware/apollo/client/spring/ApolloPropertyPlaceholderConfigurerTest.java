package com.hawkware.apollo.client.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.hawkware.apollo.client.PropertyService;
import com.hawkware.apollo.client.spring.ApolloPropertyPlaceholderConfigurer;

public class ApolloPropertyPlaceholderConfigurerTest {

    private ApolloPropertyPlaceholderConfigurer apolloPropertyPlaceholderConfigurer;

    private PropertyService propertyService;

    @Before
    public void setUp() throws Exception {
	apolloPropertyPlaceholderConfigurer = new ApolloPropertyPlaceholderConfigurer();
	propertyService = Mockito.mock(PropertyService.class);
	apolloPropertyPlaceholderConfigurer.setPropertyService(propertyService);
    }

    // @Test
    // public void testResolvePlaceholderStringProperties() {
    // String expectedValue = "somethingnonnull";
    // Mockito.when(propertyService.getProperty(Mockito.anyString())).thenReturn(expectedValue);
    // String actualValue =
    // apolloPropertyPlaceholderConfigurer.resolvePlaceholder(null, null);
    // assertEquals(expectedValue, actualValue);
    // }
    //
    // @Test
    // public void testResolvePlaceholderStringPropertiesInt() {
    // String expectedValue = "somethingrandomandnonnull";
    // Mockito.when(propertyService.getProperty(Mockito.anyString())).thenReturn(expectedValue);
    // String actualValue =
    // apolloPropertyPlaceholderConfigurer.resolvePlaceholder(null, null, 0);
    // assertEquals(expectedValue, actualValue);
    // }

    @Test
    public void testGetPropertyService() {
	String expectedApplication = "apollo";
	String expectedServerUrl = "http://localhost:8140/apollo";
	String expectedContext = "dev";

	apolloPropertyPlaceholderConfigurer = new ApolloPropertyPlaceholderConfigurer();
	apolloPropertyPlaceholderConfigurer.setApplication(expectedApplication);
	apolloPropertyPlaceholderConfigurer.setServerUrl(expectedServerUrl);
	apolloPropertyPlaceholderConfigurer.setContext(expectedContext);

	PropertyService propertyService = apolloPropertyPlaceholderConfigurer.getPropertyService();

	assertNotNull(propertyService);
	assertEquals(expectedApplication, propertyService.getApplication());
	assertEquals(expectedServerUrl, propertyService.getServerUrl());
	assertEquals(expectedContext, propertyService.getContext());
    }
}
