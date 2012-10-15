package com.hawkware.apollo.http;

import java.util.Map;

public interface UrlInvoker {

    public abstract Payload invoke(String url);

    public abstract Payload invoke(String url, Map<String, String> headers);

    public abstract Payload invoke(String url, String payload);

    public abstract Payload invoke(String url, String payload, Map<String, String> headers);

}