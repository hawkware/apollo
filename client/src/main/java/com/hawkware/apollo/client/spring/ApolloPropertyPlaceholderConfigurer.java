package com.hawkware.apollo.client.spring;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.hawkware.apollo.client.PropertyService;
import com.hawkware.apollo.client.model.Property;

public class ApolloPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(ApolloPropertyPlaceholderConfigurer.class);

    private PropertyService propertyService;

    private String serverUrl;

    private String application;

    private String context;

    public ApolloPropertyPlaceholderConfigurer() {
    }

    @Override
    protected void loadProperties(Properties props) throws IOException {
	logger.info("loading properties");
	if (props != null) {
	    List<Property> propsList = getPropertyService().getProperties();
	    for (Property prop : propsList) {
		props.put(prop.getName(), prop.getValue());
	    }
	    logger.info("loaded following properties from apollo= [" + props + "]");
	}
	super.loadProperties(props);
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
