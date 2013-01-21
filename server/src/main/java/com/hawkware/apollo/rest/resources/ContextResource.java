package com.hawkware.apollo.rest.resources;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "context")
public class ContextResource {
    private String name;

    private String application;

    private List<ServerResource> serverResources;

    public ContextResource() {
    }

    public ContextResource(String name, List<ServerResource> serverResources) {
	this.name = name;
	this.serverResources = serverResources;
    }

    @XmlElements(value = { @XmlElement(name = "server", type = ServerResource.class) })
    public List<ServerResource> getServerResources() {
	return serverResources;
    }

    public void setServerResources(List<ServerResource> serverResources) {
	this.serverResources = serverResources;
    }

    @XmlAttribute(name = "name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getApplication() {
	return application;
    }

    public void setApplication(String application) {
	this.application = application;
    }

    @Override
    public String toString() {
	return "ContextResource [name=" + name + ", serverResources=" + serverResources + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((application == null) ? 0 : application.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((serverResources == null) ? 0 : serverResources.hashCode());
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
	ContextResource other = (ContextResource) obj;
	if (application == null) {
	    if (other.application != null)
		return false;
	} else if (!application.equals(other.application))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (serverResources == null) {
	    if (other.serverResources != null)
		return false;
	} else if (serverResources.size() != (other.serverResources.size()))
	    return false;
	return true;
    }

}
