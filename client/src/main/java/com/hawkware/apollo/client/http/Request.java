package com.hawkware.apollo.client.http;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private Map<String, String> headers = new HashMap<String, String>();

    private String payload = "";

    private String url;

    public Map<String, String> getHeaders() {
	return headers;
    }

    public void addHeader(String name, String value) {
	headers.put(name, value);
    }

    public void setHeaders(Map<String, String> headers) {
	this.headers = headers;
    }

    public String getPayload() {
	return payload;
    }

    public void setPayload(String payload) {
	this.payload = payload;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    @Override
    public String toString() {
	return "Request [headers=" + headers + ", payload=" + payload + ", url=" + url + "]";
    }

}
