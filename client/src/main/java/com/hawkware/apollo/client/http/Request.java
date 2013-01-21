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
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((headers == null) ? 0 : headers.hashCode());
	result = prime * result + ((payload == null) ? 0 : payload.hashCode());
	result = prime * result + ((url == null) ? 0 : url.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Request other = (Request) obj;
	if (headers == null) {
	    if (other.headers != null)
		return false;
	} else if (!headers.equals(other.headers))
	    return false;
	if (payload == null) {
	    if (other.payload != null)
		return false;
	} else if (!payload.equals(other.payload))
	    return false;
	if (url == null) {
	    if (other.url != null)
		return false;
	} else if (!url.equals(other.url))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Request [headers=" + headers + ", payload=" + payload + ", url=" + url + "]";
    }

}
