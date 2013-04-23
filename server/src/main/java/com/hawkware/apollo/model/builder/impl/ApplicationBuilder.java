package com.hawkware.apollo.model.builder.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.model.Property;
import com.hawkware.apollo.model.builder.Builder;

public class ApplicationBuilder implements Builder<Application> {

	private String name;
	private Map<String, Property> properties = new HashMap<String, Property>();

	@Override
	public Application build() {
		Application app = new Application();
		app.setName(this.name);
		app.setProperties(this.properties.values());
		return app;
	}

	public ApplicationBuilder name(String name) {
		this.name = name;
		return this;
	}

	public ApplicationBuilder property(Property property) {
		if (property != null) {
			this.properties.put(property.getName(), property);
		}
		return this;
	}

	public ApplicationBuilder properties(Collection<Property> properties) {
		if (properties != null) {
			for (Property property : properties) {
				property(property);
			}
		}
		return this;
	}
}
