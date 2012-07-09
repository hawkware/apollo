package com.hawkware.apollo.rest.converter.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	resource.setContext(context);
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

    public List<PropertyResource> from(Collection<Property> properties) {
	List<PropertyResource> resources = new ArrayList<PropertyResource>();
	for (Property property : properties) {
	    for (Entry<String, String> value : property.getValuesByContext().entrySet()) {
		String context = value.getKey();
		PropertyResource resource = from(property, context);
		if (resource != null) {
		    resources.add(resource);
		}
	    }
	}
	return resources;
    }

    public Collection<Property> to(Collection<PropertyResource> resources) {
	Map<String, Property> properties = new HashMap<String, Property>();

	for (PropertyResource resource : resources) {
	    Property property = to(resource);

	    if (property != null) {
		if (properties.get(property.getName()) != null) {
		    Property current = properties.get(property.getName());
		    current.setValuesByContext(property.getValuesByContext());
		} else {
		    properties.put(property.getName(), property);
		}
	    }
	}

	return properties.values();
    }
}
