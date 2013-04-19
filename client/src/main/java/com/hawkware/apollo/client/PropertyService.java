package com.hawkware.apollo.client;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawkware.apollo.client.http.DefaultHttpService;
import com.hawkware.apollo.client.http.HttpService;
import com.hawkware.apollo.client.http.Request;
import com.hawkware.apollo.client.http.Response;
import com.hawkware.apollo.client.model.Application;
import com.hawkware.apollo.client.model.Property;

public class PropertyService {

<<<<<<< HEAD:client/src/main/java/com/hawkware/apollo/client/services/PropertyService.java
	private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);
=======
    private static final String APPLICATION_URI_FORMAT = "/application/%s";

    private static final String PROPERTY_URI_FORMAT = "/application/%s/property/%s";

    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);
>>>>>>> upstream/master:client/src/main/java/com/hawkware/apollo/client/PropertyService.java

	private JAXBContext jaxbContext;

	private HttpService httpService;

	private String application;

	private String serverUrl;

<<<<<<< HEAD:client/src/main/java/com/hawkware/apollo/client/services/PropertyService.java
	private String context;
=======
    private String environment;
>>>>>>> upstream/master:client/src/main/java/com/hawkware/apollo/client/PropertyService.java

	public PropertyService() {
		try {
			jaxbContext = JAXBContext.newInstance(Property.class, Application.class);
		} catch (JAXBException e) {
			throw new RuntimeException("could not instantiate jaxb context", e);
		}
		httpService = new DefaultHttpService();
	}

	public String getProperty(String name) {

<<<<<<< HEAD:client/src/main/java/com/hawkware/apollo/client/services/PropertyService.java
		Request request = new Request();
		request.setUrl(String.format(serverUrl + "/application/%s/property/%s", application, name));
		if (context != null && context.trim().length() > 0) {
			request.addHeader("Context", context);
		}
=======
	Request request = new Request();
	request.setUrl(String.format(serverUrl + PROPERTY_URI_FORMAT, application, name));
	if (environment != null && environment.trim().length() > 0) {
	    request.addHeader("Environment", environment);
	}
>>>>>>> upstream/master:client/src/main/java/com/hawkware/apollo/client/PropertyService.java

		Response response = httpService.execute(request);

		String payload = response.getPayload();

		logger.debug("payload=" + payload);

		String value = null;
		if (payload != null && payload.trim().length() > 0) {
			Property prop = (Property) convert(payload);
			if (prop != null) {
				value = prop.getValue();
			}
			logger.debug("property=" + prop);
		} else {
			logger.warn("property [" + name + "] could not be found for application [" + application + "]");
		}
		return value;
	}

	public List<Property> getProperties() {
		List<Property> properties = new ArrayList<Property>();

<<<<<<< HEAD:client/src/main/java/com/hawkware/apollo/client/services/PropertyService.java
		Request request = new Request();
		request.setUrl(String.format(serverUrl + "/application/%s", application));
		if (context != null && context.trim().length() > 0) {
			request.addHeader("Context", context);
		}
=======
	Request request = new Request();
	request.setUrl(String.format(serverUrl + APPLICATION_URI_FORMAT, application));
	if (environment != null && environment.trim().length() > 0) {
	    request.addHeader("Environment", environment);
	}
>>>>>>> upstream/master:client/src/main/java/com/hawkware/apollo/client/PropertyService.java

		Response response = httpService.execute(request);

<<<<<<< HEAD:client/src/main/java/com/hawkware/apollo/client/services/PropertyService.java
		String payload = response.getPayload();

		logger.debug("payload=" + payload);
=======
	String payload = response.getPayload();
	System.out.println("payload=" + payload);
	logger.debug("payload=" + payload);
>>>>>>> upstream/master:client/src/main/java/com/hawkware/apollo/client/PropertyService.java

		if (payload != null) {
			Application app = (Application) convert(payload);
			if (app != null) {
				properties.addAll(app.getProperties());
			}
			logger.debug("properties=" + application);
		}
		return properties;
	}

	private Object convert(String payload) {
		Object object = null;
		try {
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			object = unmarshaller.unmarshal(new StringReader(payload));
		} catch (JAXBException jbe) {
			throw new RuntimeException("could not unmarshall payload = [" + payload + "]");
		}
		return object;
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

<<<<<<< HEAD:client/src/main/java/com/hawkware/apollo/client/services/PropertyService.java
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
=======
    public String getEnvironment() {
	return environment;
    }

    public void setEnvironment(String environment) {
	this.environment = environment;
    }
>>>>>>> upstream/master:client/src/main/java/com/hawkware/apollo/client/PropertyService.java

	void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}

}
