package com.hawkware.apollo.client.http;

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
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpService {

    private static final Logger logger = LoggerFactory.getLogger(HttpService.class);

    public static final int MAX_TOTAL_CONNECTION = 300;
    public static final int MAX_CONNECTIONS_PER_ROUTE = 100;
    public static final int TIMEOUT_CONNECT = 15000;
    public static final int TIMEOUT_READ = 15000;

    private final HttpClient client;

    private int maxTotalConnection = MAX_TOTAL_CONNECTION;

    private int maxConnectionsPerRoute = MAX_CONNECTIONS_PER_ROUTE;

    private int readTimeout = TIMEOUT_READ;

    private int connectTimeout = TIMEOUT_CONNECT;

    public HttpService() {

	HttpParams connManagerParams = new BasicHttpParams();
	HttpConnectionParams.setConnectionTimeout(connManagerParams, connectTimeout);
	HttpConnectionParams.setSoTimeout(connManagerParams, readTimeout);

	ThreadSafeClientConnManager cmgr = new ThreadSafeClientConnManager();
	cmgr.setDefaultMaxPerRoute(maxConnectionsPerRoute);
	cmgr.setMaxTotal(maxTotalConnection);

	client = new DefaultHttpClient(cmgr, connManagerParams);
    }

    public Response execute(Request request) {
	logger.debug("executing httpRequest [" + request + "]");
	Response response = null;

	logger.debug("initialising http clent ");

	logger.debug("getting http method");
	HttpUriRequest method = createMethod(request.getUrl(), request.getPayload(),
		request.getHeaders().get("Content-Type"));

	logger.debug("initialising response handler");
	ResponseHandler<Response> handler = createHandler();

	try {
	    response = client.execute(method, handler);

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
	return response;
    }

    private HttpUriRequest createMethod(String url, String payload, String contentType) {
	HttpUriRequest method;
	if (payload == null || payload.trim().length() == 0) {
	    method = new HttpGet(url);
	} else {
	    method = new HttpPost(url);
	    method.setHeader("Content-Type", contentType != null ? contentType : "application/xml");
	    logger.debug("setting post method payload = [" + payload + "]");
	    try {
		((HttpPost) method).setEntity(new StringEntity(payload));
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }

	}
	return method;
    }

    private ResponseHandler<Response> createHandler() {
	ResponseHandler<Response> handler = new ResponseHandler<Response>() {
	    public Response handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
		HttpEntity entity = httpResponse.getEntity();

		if (entity != null) {
		    Response response = new Response();

		    String responseAsString = EntityUtils.toString(entity);
		    response.setPayload(responseAsString);

		    Header[] headers = httpResponse.getAllHeaders();
		    Map<String, String> headersMap = createHeaderMap(headers);
		    response.setHeaders(headersMap);

		    response.setStatus(httpResponse.getStatusLine().getStatusCode());

		    return response;
		} else {
		    return null;
		}
	    }

	};
	return handler;
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

    public void setMaxTotalConnection(int maxTotalConnection) {
	this.maxTotalConnection = maxTotalConnection;
    }

    public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
	this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }

    public void setReadTimeout(int readTimeout) {
	this.readTimeout = readTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
	this.connectTimeout = connectTimeout;
    }

}
