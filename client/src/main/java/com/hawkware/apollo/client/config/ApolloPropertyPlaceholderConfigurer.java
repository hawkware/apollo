package com.hawkware.apollo.client.config;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.hawkware.apollo.client.services.PropertyService;

public class ApolloPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private PropertyService propertyService;

    private String serverUrl;

    private String application;

    private String context;

    public ApolloPropertyPlaceholderConfigurer() {
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
	return propertyService.getProperty(placeholder);
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
	return propertyService.getProperty(placeholder);
    }

    public PropertyService getPropertyService() {
	if (propertyService == null) {
	    propertyService = new PropertyService();
	    propertyService.setApplication(application);
	    propertyService.setServerUrl(serverUrl);
	    propertyService.setContext(context);
	}
	return propertyService;
    }

    public void setPropertyService(PropertyService propertyService) {
	this.propertyService = propertyService;
    }

    public void setServerUrl(String serverUrl) {
	this.serverUrl = serverUrl;
    }

    public void setApplication(String application) {
	this.application = application;
    }

    public void setContext(String context) {
	this.context = context;
    }

}
