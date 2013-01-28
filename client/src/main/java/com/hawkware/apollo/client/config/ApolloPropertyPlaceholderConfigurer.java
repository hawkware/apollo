package com.hawkware.apollo.client.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.hawkware.apollo.client.services.PropertyService;

public class ApolloPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(ApolloPropertyPlaceholderConfigurer.class);

    private PropertyService propertyService;

    private String serverUrl;

    private String application;

    private String context;

    public ApolloPropertyPlaceholderConfigurer() {
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
	logger.debug("resolving placeholder=[" + placeholder + "], properties=[" + props + "]");
	return propertyService.getProperty(placeholder);
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
	logger.debug("resolving placeholder=[" + placeholder + "], properties=[" + props + "], systemPropertiesMode=["
		+ systemPropertiesMode + "]");
	return propertyService.getProperty(placeholder);
    }

    PropertyService getPropertyService() {
	if (propertyService == null) {
	    logger.debug("setting up a new propertyservice from properties");
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
