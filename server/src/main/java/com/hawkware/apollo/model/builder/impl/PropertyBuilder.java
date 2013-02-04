package com.hawkware.apollo.model.builder.impl;

import java.util.HashMap;
import java.util.Map;

import com.hawkware.apollo.model.Property;
import com.hawkware.apollo.model.builder.Builder;

public class PropertyBuilder implements Builder<Property> {
    private String name;

    private long timeToLive;

    private Map<String, String> valuesByContext = new HashMap<String, String>();

    @Override
    public Property build() {
	Property property = new Property();
	property.setName(this.name);
	if (this.timeToLive > 0) {
	    property.setTimeToLive(this.timeToLive);
	}
	property.setValuesByContext(this.valuesByContext);
	return property;
    }

    public PropertyBuilder name(String name) {
	this.name = name;
	return this;
    }

    public PropertyBuilder timeToLive(long timeToLive) {
	this.timeToLive = timeToLive;
	return this;
    }

    public PropertyBuilder value(String context, String value) {
	if (context != null) {
	    this.valuesByContext.put(context, value);
	} else {
	    throw new IllegalArgumentException("context cannot be null");
	}

	return this;
    }
}
