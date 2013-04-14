package com.hawkware.apollo.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "environments")
public class EnvironmentResources {

    List<EnvironmentResource> environmentResources = new ArrayList<EnvironmentResource>();

    public EnvironmentResources() {
    }

    public EnvironmentResources(List<EnvironmentResource> environmentResources) {
	this.environmentResources = environmentResources;
    }

    @XmlElements(value = { @XmlElement(name = "environment", type = EnvironmentResource.class) })
    public List<EnvironmentResource> getEnvironmentResources() {
	return environmentResources;
    }

    public void setEnvironmentResources(List<EnvironmentResource> environmentResources) {
	this.environmentResources = environmentResources;
    }

    @Override
    public String toString() {
	return "EnvironmentResources [environmentResources=" + environmentResources + "]";
    }

}
