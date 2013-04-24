package com.hawkware.apollo.client.http;

public class Response extends Request {
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", getHeaders()=" + getHeaders() + ", getPayload()=" + getPayload() + "]";
	}
}
