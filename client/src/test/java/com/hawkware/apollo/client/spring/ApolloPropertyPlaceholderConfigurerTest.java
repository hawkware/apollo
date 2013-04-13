package com.hawkware.apollo.client.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.hawkware.apollo.client.PropertyClient;
import com.hawkware.apollo.client.spring.ApolloPropertyPlaceholderConfigurer;

public class ApolloPropertyPlaceholderConfigurerTest {

    private ApolloPropertyPlaceholderConfigurer apolloPropertyPlaceholderConfigurer;

    private PropertyClient propertyClient;

    @Before
    public void setUp() throws Exception {
	apolloPropertyPlaceholderConfigurer = new ApolloPropertyPlaceholderConfigurer();
	propertyClient = Mockito.mock(PropertyClient.class);
	apolloPropertyPlaceholderConfigurer.setPropertyService(propertyClient);
    }

    // @Test
    // public void testResolvePlaceholderStringProperties() {
    // String expectedValue = "somethingnonnull";
    // Mockito.when(propertyClient.getProperty(Mockito.anyString())).thenReturn(expectedValue);
    // String actualValue =
    // apolloPropertyPlaceholderConfigurer.resolvePlaceholder(null, null);
    // assertEquals(expectedValue, actualValue);
    // }
    //
    // @Test
    // public void testResolvePlaceholderStringPropertiesInt() {
    // String expectedValue = "somethingrandomandnonnull";
    // Mockito.when(propertyClient.getProperty(Mockito.anyString())).thenReturn(expectedValue);
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

	PropertyClient propertyClient = apolloPropertyPlaceholderConfigurer.getPropertyService();

	assertNotNull(propertyClient);
	assertEquals(expectedApplication, propertyClient.getApplication());
	assertEquals(expectedServerUrl, propertyClient.getServerUrl());
	assertEquals(expectedContext, propertyClient.getContext());
    }
}
