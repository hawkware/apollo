package com.hawkware.apollo.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "applications")
public class ApplicationResources {

    List<ApplicationResource> applicationResources = new ArrayList<ApplicationResource>();

    @XmlElements(value = { @XmlElement(name = "application", type = ApplicationResource.class) })
    public List<ApplicationResource> getApplicationResources() {
	return applicationResources;
    }

    public void setApplicationResources(List<ApplicationResource> applicationResources) {
	this.applicationResources = applicationResources;
    }

    @Override
    public String toString() {
	return "ApplicationResources [applicationResources=" + applicationResources + "]";
    }

}
