package com.hawkware.apollo.model;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Application {

    private String id;

    private String name;

    private Map<String, Property> properties = new ConcurrentHashMap<String, Property>();

    private Map<String, ApplicationContext> contexts = new ConcurrentHashMap<String, ApplicationContext>();

    public Application() {
    }

    public Application(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void addProperties(Collection<Property> props) {
	for (Property prop : props) {
	    addProperty(prop);
	}
    }

    public void removeProperty(String name) {
	properties.remove(name);
    }

    public void addProperty(Property property) {

	Property propToUpdate = properties.get(property.getName());
	if (propToUpdate != null) {
	    if (propToUpdate.getValuesByContext() != null) {
		propToUpdate.getValuesByContext().putAll(property.getValuesByContext());
	    } else {
		propToUpdate.setValuesByContext(property.getValuesByContext());
	    }
	} else {
	    properties.put(property.getName(), property);
	}

    }

    public void addContext(ApplicationContext context) {
	ApplicationContext ctxToUpdate = contexts.get(context.getName());
	if (ctxToUpdate != null) {
	    if (ctxToUpdate.getServers() != null) {
		ctxToUpdate.getServers().addAll(context.getServers());
	    } else {
		ctxToUpdate.setServers(context.getServers());
	    }
	} else {
	    contexts.put(context.getName(), context);
	}
    }

    public Property getProperty(String name) {
	return properties.get(name);
    }

    public ApplicationContext getContext(String name) {
	return contexts.get(name);
    }

    public Collection<Property> getProperties() {
	return properties.values();
    }

    public void setProperties(Collection<Property> properties) {
	if (properties != null) {
	    for (Property property : properties) {
		addProperty(property);
	    }
	}
    }

    public Collection<ApplicationContext> getContexts() {
	return contexts.values();
    }

    public void setContexts(Collection<ApplicationContext> contexts) {
	if (contexts != null) {
	    for (ApplicationContext context : contexts) {
		addContext(context);
	    }
	}

    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return "Application [id=" + id + ", name=" + name + ", properties=" + properties + ", contexts=" + contexts
		+ "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((contexts == null) ? 0 : contexts.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
	Application other = (Application) obj;
	if (contexts == null) {
	    if (other.contexts != null)
		return false;
	} else if (contexts.size() != other.contexts.size())
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (properties == null) {
	    if (other.properties != null)
		return false;
	} else if (properties.size() != other.properties.size())
	    return false;
	return true;
    }

}
