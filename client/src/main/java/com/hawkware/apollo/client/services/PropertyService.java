package com.hawkware.apollo.client.services;

import java.io.StringReader;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.hawkware.apollo.client.http.DefaultHttpService;
import com.hawkware.apollo.client.http.HttpService;
import com.hawkware.apollo.client.http.Request;
import com.hawkware.apollo.client.http.Response;
import com.hawkware.apollo.client.model.Property;

public class PropertyService {

    private JAXBContext jaxbContext;

    private HttpService httpService;

    private String application;

    private String serverUrl;

    private String context;

    public PropertyService() {
	try {
	    jaxbContext = JAXBContext.newInstance(Property.class);
	} catch (JAXBException e) {
	    throw new RuntimeException("could not instantiate jaxb context");
	}
	httpService = new DefaultHttpService();
    }

    public String getProperty(String name) {

	Request request = new Request();
	request.setUrl(String.format(serverUrl + "/application/%s/property/%s", application, name));
	if (context != null && context.trim().length() > 0) {
	    request.addHeader("context", context);
	}

	Response response = httpService.execute(request);

	String payload = response.getPayload();

	System.out.println("payload=" + payload);
	String value = null;
	if (payload != null) {
	    Property prop = convert(payload);
	    if (prop != null) {
		value = prop.getValue();
	    }
	    System.out.println("prop=" + prop);
	}
	return value;
    }

    public Properties getProperties() {
	Properties properties = new Properties();

	return properties;
    }

    private Property convert(String payload) {
	Property property = null;
	try {
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    Object object = unmarshaller.unmarshal(new StringReader(payload));
	    property = (Property) object;
	} catch (JAXBException jbe) {
	    throw new RuntimeException("could not unmarshall payload = [" + payload + "]");
	}
	return property;
    }

    public String getApplication() {
	return application;
    }

    public void setApplication(String application) {
	this.application = application;
    }

    public String getServerUrl() {
	return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
	this.serverUrl = serverUrl;
    }

    public String getContext() {
	return context;
    }

    public void setContext(String context) {
	this.context = context;
    }

    void setHttpService(HttpService httpService) {
	this.httpService = httpService;
    }

}
