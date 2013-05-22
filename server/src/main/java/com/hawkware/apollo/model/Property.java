package com.hawkware.apollo.model;

import java.util.HashMap;
import java.util.Map;

public class Property {
	private static final long ONE_DAY_IN_SECS = 24 * 60 * 60;
	private String name;

    private Map<String, String> valuesByContext = new HashMap<String, String>();

	private long timeToLive = ONE_DAY_IN_SECS;

	public Property() {
	}

	public Property(String name) {
		this.name = name;
		this.valuesByContext = new HashMap<String, String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getValuesByContext() {
		return valuesByContext;
	}

	public String getValue(String context) {
		String value = null;
		if (valuesByContext != null) {
			value = valuesByContext.get(context);
			if (value == null) {
                String DEFAULT_KEY = "default";
                value = valuesByContext.get(DEFAULT_KEY);
			}
		}
		return value;
	}

	public boolean addValue(String context, String value) {
		if (valuesByContext != null) {
			valuesByContext.put(context, value);
			return true;
		}
		return false;
	}

	public void setValuesByContext(Map<String, String> valuesByContext) {
		if (this.valuesByContext != null && valuesByContext != null) {
			this.valuesByContext.putAll(valuesByContext);
		} else {
			this.valuesByContext = valuesByContext;
		}
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (timeToLive ^ (timeToLive >>> 32));
		result = prime * result + ((valuesByContext == null) ? 0 : valuesByContext.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timeToLive != other.timeToLive)
			return false;
		if (valuesByContext == null) {
			if (other.valuesByContext != null)
				return false;
		} else if (!valuesByContext.equals(other.valuesByContext))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Property [name=" + name + ", valuesByContext=" + valuesByContext + ", timeToLive=" + timeToLive + "]";
	}

}
