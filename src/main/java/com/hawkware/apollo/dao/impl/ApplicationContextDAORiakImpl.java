package com.hawkware.apollo.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.dao.query.Criterion;
import com.hawkware.apollo.http.Payload;
import com.hawkware.apollo.http.UrlInvoker;
import com.hawkware.apollo.http.impl.UrlInvokerImpl;
import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.builder.impl.ServerBuilder;

public class ApplicationContextDAORiakImpl extends GenericDAO<ApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextDAORiakImpl.class);

    private UrlInvoker urlInvoker;

    private String databaseUrl;

    public static void main(String[] args) {
	ApplicationContextDAORiakImpl dao = new ApplicationContextDAORiakImpl();
	dao.setDatabaseUrl("http://localhost:8091/riak/apollo");
	dao.setUrlInvoker(new UrlInvokerImpl());

	ApplicationContext context = new ApplicationContext();
	context.setName("production");
	context.setServers(Arrays.asList(new ServerBuilder().hostName("localhost").build(), new ServerBuilder()
		.hostName("api.dev.oriviling.com").build()));

	Object result = dao.save(context);
	System.out.println("saved:" + result);

	ApplicationContext actualContext = dao.get("context-production");

	System.out.println("returned context = " + actualContext);
    }

    @Override
    public Object save(ApplicationContext context) {

	Map<String, String> headers = new HashMap<String, String>();
	headers.put("Content-Type", "application/json");
	Payload payload = urlInvoker.invoke(databaseUrl, toJson(context), headers);

	createLink(payload.getHeaders().get("Location"), "context-" + context.getName());
	return payload.getHeaders().get("Location");
    }

    private void createLink(String location, String linkName) {
	String url = databaseUrl + "/" + linkName;
	Map<String, String> header = new HashMap<String, String>();
	header.put("Link", "<" + location + ">; riaktag=\"linksto\"");
	String payload = "{\"link\" : \"" + location + "\" }";
	urlInvoker.invoke(url, payload, header);
    }

    @Override
    public ApplicationContext get(Object key) {
	String url = databaseUrl + "/" + key.toString() + "/_,_,_";
	Payload response = urlInvoker.invoke(url);

	ApplicationContext context = fromJson(response.getPayload());
	return context;
    }

    private String toJson(ApplicationContext context) {
	StringWriter writer = new StringWriter();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    mapper.writeValue(writer, context);
	} catch (JsonGenerationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (JsonMappingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return writer.toString();
    }

    private ApplicationContext fromJson(String response) {

	if (response.contains("Content-Type: multipart/mixed; boundary=")) {
	    response = extractJsonFromMultipartResponse(response);
	}

	ObjectMapper mapper = new ObjectMapper();
	ApplicationContext context = null;
	try {
	    context = mapper.readValue(response.getBytes(), ApplicationContext.class);
	} catch (JsonParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (JsonMappingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return context;
    }

    private String extractJsonFromMultipartResponse(String response) {
	BufferedReader reader = new BufferedReader(new StringReader(response));
	String line = null;
	String json = null;
	try {
	    while ((line = reader.readLine()) != null) {
		if (line.startsWith("{")) {
		    json = line;
		    break;
		}
	    }
	    reader.close();
	} catch (IOException e) {

	}

	return json;
    }

    @Override
    public ApplicationContext get(String keyName, Object keyValue) {
	throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ApplicationContext> get(Map<String, Criterion> criteriaMap) {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(ApplicationContext object) {
	throw new UnsupportedOperationException();
    }

    public void setUrlInvoker(UrlInvoker urlInvoker) {
	this.urlInvoker = urlInvoker;
    }

    public void setDatabaseUrl(String databaseUrl) {
	this.databaseUrl = databaseUrl;
    }
}
