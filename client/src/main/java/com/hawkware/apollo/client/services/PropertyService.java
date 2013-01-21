package com.hawkware.apollo.client.services;

import java.io.StringReader;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.hawkware.apollo.client.http.HttpService;
import com.hawkware.apollo.client.http.Request;
import com.hawkware.apollo.client.http.Response;
import com.hawkware.apollo.client.model.Property;

public class PropertyService {

    private JAXBContext context;

    private HttpService httpService;

    private String application;

    private String serverUrl;

    public PropertyService() {
	try {
	    context = JAXBContext.newInstance(Property.class);
	} catch (JAXBException e) {
	    throw new RuntimeException("could not instantiate jaxb context");
	}
	httpService = new HttpService();
    }

    public String getProperty(String name) {

	Request request = new Request();
	request.setUrl(String.format(serverUrl + "/application/%s/property/%s", application, name));

	Response response = httpService.execute(request);

	String payload = response.getPayload();
	Property prop = convert(payload);
	return prop.getValue();
    }

    public Properties getProperties() {
	Properties properties = new Properties();

	return properties;
    }

    private Property convert(String payload) {
	Property property = null;
	try {
	    Unmarshaller unmarshaller = context.createUnmarshaller();
	    Object object = unmarshaller.unmarshal(new StringReader(payload));
	    property = (Property) object;
	} catch (JAXBException jbe) {
	    throw new RuntimeException("could not unmarshall payload = [" + payload + "]");
	}
	return property;
    }

    public void setApplication(String application) {
	this.application = application;
    }

    public void setServerUrl(String serverUrl) {
	this.serverUrl = serverUrl;
    }

    public static void main(String[] args) throws JAXBException {
	PropertyService ps = new PropertyService();
	ps.setServerUrl("http://localhost:8184/apollo");
	ps.setApplication("apollo");
	String resp = ps.getProperty("mongodbhost");
	System.out.println(resp);
	System.out.println(ps.convert(resp));
    }

}
