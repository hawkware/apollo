package com.hawkware.apollo.constants;

public enum HttpHeader {
    ContentType("Content-Type"), Location("Location"), Link("Link"), XRiakMEta("X-Riak-Meta-");

    private String value;

    private HttpHeader(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }
}
