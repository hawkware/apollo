package com.hawkware.apollo.client.config;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.hawkware.apollo.client.services.PropertyService;

public class ApolloPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private PropertyService propertyService;

    private String application;

    private String serverUrl;

    public ApolloPropertyPlaceholderConfigurer() {
	propertyService = new PropertyService();
	propertyService.setApplication(application);
	propertyService.setServerUrl(serverUrl);
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
	return propertyService.getProperty(placeholder);
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
	return propertyService.getProperty(placeholder);
    }

    public void setApplication(String application) {
	this.application = application;
    }

    public void setServerUrl(String serverUrl) {
	this.serverUrl = serverUrl;
    }

}
