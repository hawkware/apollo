package com.hawkware.apollo.client.http;

public class HttpException extends RuntimeException {
    public HttpException(Object cause, Throwable source) {
	super("error processing request = [" + cause + "]", source);
    }
}
