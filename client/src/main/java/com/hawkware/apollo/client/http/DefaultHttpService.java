package com.hawkware.apollo.client.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

public final class DefaultHttpService implements HttpService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultHttpService.class);

    private final HttpClient client;

    private int maxTotalConnection = MAX_TOTAL_CONNECTION;

    private int maxConnectionsPerRoute = MAX_CONNECTIONS_PER_ROUTE;

    private int readTimeout = TIMEOUT_READ;

    private int connectTimeout = TIMEOUT_CONNECT;

    public DefaultHttpService() {

	HttpParams connManagerParams = new BasicHttpParams();
	HttpConnectionParams.setConnectionTimeout(connManagerParams, connectTimeout);
	HttpConnectionParams.setSoTimeout(connManagerParams, readTimeout);

	ThreadSafeClientConnManager cmgr = new ThreadSafeClientConnManager();
	cmgr.setDefaultMaxPerRoute(maxConnectionsPerRoute);
	cmgr.setMaxTotal(maxTotalConnection);

	client = new DefaultHttpClient(cmgr, connManagerParams);
    }

    DefaultHttpService(HttpClient httpClient) {
	this.client = httpClient;
    }

    @Override
    public Response execute(Request request) {
	logger.debug("executing httpRequest [" + request + "]");
	Response response = null;

	logger.debug("initialising http clent ");

	logger.debug("getting http method");
	HttpUriRequest httpRequest = createHttpRequest(request);

	logger.debug("initialising response handler");
	ResponseHandler<Response> handler = createHandler();

	response = executeInternal(httpRequest, handler);

	return response;
    }

    Response executeInternal(HttpUriRequest request, ResponseHandler<Response> handler) {
	Response response = null;
	try {
	    response = client.execute(request, handler);

	} catch (ClientProtocolException cpe) {
	    throw new HttpException(request, cpe);
	} catch (IOException ioe) {
	    throw new HttpException(request, ioe);
	} finally {
	    if (request != null) {
		logger.debug("closing the http connection");
		request.abort();
	    }
	}
	return response;
    }

    HttpUriRequest createHttpRequest(Request request) {
	String url = request.getUrl();
	String payload = request.getPayload();
	String contentType = request.getHeaders().get("Content-Type");

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

	for (Entry<String, String> header : request.getHeaders().entrySet()) {
	    method.setHeader(header.getKey(), header.getValue());
	}
	return method;
    }

    ResponseHandler<Response> createHandler() {
	ResponseHandler<Response> handler = new ResponseHandler<Response>() {
	    public Response handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
		HttpEntity entity = httpResponse.getEntity();

		if (entity != null) {
		    Response response = new Response();

		    String responseAsString = EntityUtils.toString(entity);
		    logger.debug("responseAsString = [" + responseAsString + "]");
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

    Map<String, String> createHeaderMap(Header[] headers) {
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
