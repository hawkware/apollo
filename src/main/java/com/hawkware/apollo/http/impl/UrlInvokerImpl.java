package com.hawkware.apollo.http.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawkware.apollo.http.Payload;
import com.hawkware.apollo.http.UrlInvoker;

public class UrlInvokerImpl implements UrlInvoker {
    private static final Logger logger = LoggerFactory.getLogger(UrlInvokerImpl.class);

    @Override
    public Payload invoke(String url) {
	return invoke(url, null, null);
    }

    @Override
    public Payload invoke(String url, Map<String, String> headers) {
	return invoke(url, null, headers);
    }

    @Override
    public Payload invoke(String url, String payload) {
	return invoke(url, payload);
    }

    @Override
    public Payload invoke(String url, String payload, Map<String, String> headers) {
	logger.debug("invoking url [" + url + "] with payload [" + payload + "]");
	Payload responsePayload = null;

	logger.debug("initialising http clent ");
	HttpClient client = new DefaultHttpClient();

	logger.debug("getting http method");
	HttpUriRequest method = getMethod(url, payload);
	if (headers != null) {
	    for (Map.Entry<String, String> entry : headers.entrySet()) {
		method.setHeader(entry.getKey(), entry.getValue());
	    }
	}

	if (method instanceof HttpPost) {
	    logger.debug("setting post method payload = [" + payload + "]");
	    try {
		((HttpPost) method).setEntity(new StringEntity(payload));
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }
	}

	logger.debug("initialising response handler");
	ResponseHandler<Payload> handler = new ResponseHandler<Payload>() {
	    public Payload handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		HttpEntity entity = response.getEntity();

		if (entity != null) {
		    Payload payload = new Payload();

		    String responseAsString = EntityUtils.toString(entity);
		    payload.setPayload(responseAsString);

		    Header[] headers = response.getAllHeaders();
		    Map<String, String> headersMap = createHeaderMap(headers);
		    payload.setHeaders(headersMap);

		    return payload;
		} else {
		    return null;
		}
	    }

	};

	try {
	    responsePayload = client.execute(method, handler);
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (method != null) {
		logger.debug("closing the http connection");
		method.abort();
	    }
	}
	return responsePayload;
    }

    private Map<String, String> createHeaderMap(Header[] headers) {
	Map<String, String> headersMap = new HashMap<String, String>();
	for (Header header : headers) {
	    StringBuffer sb = new StringBuffer();
	    for (HeaderElement element : header.getElements()) {
		if (sb.toString().trim().length() > 0) {
		    sb.append(",");
		}
		sb.append(element.getName());
	    }

	    headersMap.put(header.getName(), sb.toString());
	}
	return headersMap;
    }

    private HttpUriRequest getMethod(String url, String payload) {
	HttpUriRequest method;
	if (payload == null) {
	    method = new HttpGet(url);
	} else {
	    method = new HttpPost(url);
	}
	return method;
    }

}
