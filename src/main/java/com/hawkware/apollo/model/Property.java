package com.hawkware.apollo.model;

import java.util.HashMap;
import java.util.Map;

public class Property {
    private static final long ONE_DAY_IN_SECS = 24 * 60 * 60;
    private String name;

    private Map<String, Object> valuesByContext;

    private long timeToLive = ONE_DAY_IN_SECS;

    public Property() {
    }

    public Property(String name) {
	this.name = name;
	this.valuesByContext = new HashMap<String, Object>();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Map<String, Object> getValuesByContext() {
	return valuesByContext;
    }

    public Object getValue(String context) {
	if (valuesByContext != null) {
	    return valuesByContext.get(context);
	}
	return null;
    }

    public boolean addValue(String context, String value) {
	if (valuesByContext != null) {
	    valuesByContext.put(context, value);
	    return true;
	}
	return false;
    }

    public void setValuesByContext(Map<String, Object> valuesByContext) {
	this.valuesByContext = valuesByContext;
    }

    public long getTimeToLive() {
	return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
	this.timeToLive = timeToLive;
    }

    @Override
    public String toString() {
	return "Property [name=" + name + ", valuesByContext=" + valuesByContext + ", timeToLive=" + timeToLive + "]";
    }

}
