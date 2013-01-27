package com.hawkware.apollo.client.http;

public interface HttpService {

    public static final int MAX_TOTAL_CONNECTION = 300;
    public static final int MAX_CONNECTIONS_PER_ROUTE = 100;
    public static final int TIMEOUT_CONNECT = 15000;
    public static final int TIMEOUT_READ = 15000;

    public abstract Response execute(Request request);

}