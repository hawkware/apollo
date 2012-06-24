package com.hawkware.apollo.model.builder.impl;

import java.util.HashMap;
import java.util.Map;

import com.hawkware.apollo.model.Property;
import com.hawkware.apollo.model.builder.Builder;

public class PropertyBuilder implements Builder<Property> {
    String name;
    long timeToLive;
    Map<String, Object> valuesByContext = new HashMap<String, Object>();

    @Override
    public Property build() {
	Property prop = new Property();
	prop.setName(this.name);
	prop.setTimeToLive(this.timeToLive);
	prop.setValuesByContext(this.valuesByContext);
	return prop;
    }

    public PropertyBuilder name(String name) {
	this.name = name;
	return this;
    }

    public PropertyBuilder timeToLive(long timeToLive) {
	this.timeToLive = timeToLive;
	return this;
    }

    public PropertyBuilder value(String context, Object value) {
	this.valuesByContext.put(context, value);
	return this;
    }
}
