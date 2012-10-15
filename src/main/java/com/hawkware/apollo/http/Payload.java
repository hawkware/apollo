package com.hawkware.apollo.http;

import java.util.Map;

public class Payload {
    private Map<String, String> headers;

    private String payload;

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

    @Override
    public String toString() {
	return "Payload [headers=" + headers + ", payload=" + payload + "]";
    }

}
