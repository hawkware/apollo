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
import com.hawkware.apollo.rest.resources.PropertyValueResource;

public class PropertyResourceConverter {

    public PropertyResource from(Property property) {
	return from(property, null);
    }

    public PropertyResource from(Property property, String context) {
	if (property == null) {
	    return null;
	}
	PropertyResource resource = new PropertyResource();

	String propName = property.getName();
	if (propName != null && propName.contains("^")) {
	    propName = propName.replaceAll("\\^", "\\.");
	}
	resource.setName(propName);

	if (context != null) {
	    PropertyValueResource pvr = new PropertyValueResource(context, property.getValue(context));
	    resource.addValue(pvr);
	} else {
	    for (Entry<String, String> value : property.getValuesByContext().entrySet()) {
		String ctx = value.getKey();
		String val = value.getValue();
		PropertyValueResource pvr = new PropertyValueResource(ctx, val);
		resource.addValue(pvr);
	    }
	}
	resource.setTimeToLive(property.getTimeToLive());
	return resource;
    }

    public Property to(PropertyResource resource) {
	if (resource == null) {
	    return null;
	}
	String propName = resource.getName();
	if (propName != null && propName.contains(".")) {
	    propName = propName.replaceAll("\\.", "\\^");
	}

	PropertyBuilder builder = new PropertyBuilder();
	builder.name(propName);
	if (resource.getTimeToLive() != null) {
	    builder.timeToLive(resource.getTimeToLive());
	}

	Collection<PropertyValueResource> valuesList = resource.getValues();
	if (valuesList != null && valuesList.size() > 0) {
	    for (PropertyValueResource pvr : resource.getValues()) {
		builder.value(pvr.getContext(), pvr.getValue());
	    }
	}
	return builder.build();
    }

    public List<PropertyResource> from(Collection<Property> properties) {
	return from(properties, null);
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
