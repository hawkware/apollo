package com.hawkware.apollo.rest.converter.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.model.Property;
import com.hawkware.apollo.model.builder.impl.ApplicationBuilder;
import com.hawkware.apollo.rest.converter.ResourceConverter;
import com.hawkware.apollo.rest.resources.ApplicationResource;
import com.hawkware.apollo.rest.resources.PropertyResource;

public class ApplicationResourceConverter extends ResourceConverter<Application, ApplicationResource> {
    @Autowired
    private PropertyResourceConverter propertyResourceConverter;

    @Override
    public ApplicationResource from(Application application) {
	ApplicationResource resource = new ApplicationResource();
	resource.setName(application.getName());
	List<PropertyResource> propertiReosurces = propertyResourceConverter.from(application.getProperties());
	resource.setProperties(propertiReosurces);
	return resource;
    }

    @Override
    public Application to(ApplicationResource resource) {
	ApplicationBuilder ab = new ApplicationBuilder();
	ab.name(resource.getName());
	if (resource.getProperties() != null) {
	    Collection<Property> properties = propertyResourceConverter.to(resource.getProperties());
	    ab.properties(properties);
	}
	return ab.build();
    }

}
