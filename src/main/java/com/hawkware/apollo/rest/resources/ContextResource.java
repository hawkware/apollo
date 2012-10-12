package com.hawkware.apollo.rest.resources;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "context")
public class ContextResource {
    private String name;

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

    @Override
    public String toString() {
	return "ContextResource [name=" + name + ", serverResources=" + serverResources + "]";
    }

}
