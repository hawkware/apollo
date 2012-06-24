package com.hawkware.apollo.rest.converter.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hawkware.apollo.model.Property;
import com.hawkware.apollo.model.builder.impl.PropertyBuilder;
import com.hawkware.apollo.rest.resources.PropertyResource;

public class PropertyResourceConverter {

    public PropertyResource from(Property property, String context) {
	if (property == null) {
	    return null;
	}
	PropertyResource resource = new PropertyResource();
	resource.setName(property.getName());
	// resource.setContext(context);
	resource.setValue(property.getValue(context));
	resource.setTimeToLive(property.getTimeToLive());
	return resource;
    }

    public Property to(PropertyResource resource) {
	if (resource == null) {
	    return null;
	}
	PropertyBuilder builder = new PropertyBuilder();
	return builder.name(resource.getName()).timeToLive(resource.getTimeToLive())
		.value(resource.getContext(), resource.getValue()).build();
    }

    public List<PropertyResource> from(Collection<Property> properties, String context) {
	List<PropertyResource> resources = new ArrayList<PropertyResource>();
	for (Property property : properties) {
	    PropertyResource resource = from(property, context);
	    if (resource != null) {
		resources.add(resource);
	    }
	}
	return resources;
    }

    public List<Property> to(Collection<PropertyResource> resources) {
	List<Property> properties = new ArrayList<Property>();
	for (PropertyResource resource : resources) {
	    Property property = to(resource);
	    if (property != null) {
		properties.add(property);
	    }
	}
	return properties;
    }

}
