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
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + status;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Response other = (Response) obj;
	if (status != other.status)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Response [status=" + status + ", getHeaders()=" + getHeaders() + ", getPayload()=" + getPayload()
		+ "]";
    }
}
