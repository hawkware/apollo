package com.hawkware.apollo.client.http;

import java.io.IOException;
import java.util.Map;

import junit.framework.Assert;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DefaultHttpServiceTest {

    private DefaultHttpService defaultHttpService;

    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
	httpClient = Mockito.mock(HttpClient.class);
	defaultHttpService = new DefaultHttpService(httpClient);
    }

    @Test
    public void testExecute() throws ClientProtocolException, IOException {
	Request req = new Request();
	req.setUrl("http://localhost:8088/test");

	Response expectedResponse = new Response();
	Mockito.when(
		httpClient.execute((HttpUriRequest) Mockito.anyObject(),
			(ResponseHandler<Response>) Mockito.anyObject())).thenReturn(expectedResponse);

	Response actualResponse = defaultHttpService.execute(req);

	Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Test(expected = HttpException.class)
    public void testExecuteInternalThrowClientProtocolException() throws ClientProtocolException, IOException {
	Request req = new Request();
	req.setUrl("http://localhost:8088/test");

	Response expectedResponse = new Response();
	Mockito.when(
		httpClient.execute((HttpUriRequest) Mockito.anyObject(),
			(ResponseHandler<Response>) Mockito.anyObject())).thenThrow(new ClientProtocolException());

	Response actualResponse = defaultHttpService.executeInternal((HttpUriRequest) Mockito.anyObject(),
		(ResponseHandler<Response>) Mockito.anyObject());
    }

    @Test(expected = HttpException.class)
    public void testExecuteInternalThrowIOException() throws ClientProtocolException, IOException {
	Request req = new Request();
	req.setUrl("http://localhost:8088/test");

	Response expectedResponse = new Response();
	Mockito.when(
		httpClient.execute((HttpUriRequest) Mockito.anyObject(),
			(ResponseHandler<Response>) Mockito.anyObject())).thenThrow(new IOException());

	Response actualResponse = defaultHttpService.executeInternal((HttpUriRequest) Mockito.anyObject(),
		(ResponseHandler<Response>) Mockito.anyObject());
    }

    @Test
    public void testExecuteInternal() throws ClientProtocolException, IOException {
	Request req = new Request();
	req.setUrl("http://localhost:8088/test");

	Response expectedResponse = new Response();
	Mockito.when(
		httpClient.execute((HttpUriRequest) Mockito.anyObject(),
			(ResponseHandler<Response>) Mockito.anyObject())).thenReturn(expectedResponse);

	Response actualResponse = defaultHttpService.executeInternal((HttpUriRequest) Mockito.anyObject(),
		(ResponseHandler<Response>) Mockito.anyObject());

	Assert.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testCreateHttpRequestWithPayloadWithDefaultContentType() {
	Request request = new Request();
	request.setUrl("http://localhost:8088/test");
	request.setPayload("somexmllikestring");

	HttpUriRequest httpRequest = defaultHttpService.createHttpRequest(request);

	Assert.assertEquals("POST", httpRequest.getMethod());
	Assert.assertEquals("application/xml", httpRequest.getHeaders("Content-Type")[0].getValue());
    }

    @Test
    public void testCreateHttpRequestWithPayloadWithSpecificContentType() {
	Request request = new Request();
	request.setUrl("http://localhost:8088/test");
	request.setPayload("somejsonlikestring");
	request.addHeader("Content-Type", "application/json");

	HttpUriRequest httpRequest = defaultHttpService.createHttpRequest(request);

	Assert.assertEquals("POST", httpRequest.getMethod());
	Assert.assertEquals("application/json", httpRequest.getHeaders("Content-Type")[0].getValue());
    }

    @Test
    public void testCreateHandler() {

    }

    @Test
    public void testCreateHeaderMap() {
	Header header1 = createHeader("Content-Type", "application/xml");
	Header header2 = createHeader("Content-Length", "12345");
	Header header3 = createHeader("Auth-Token", "`osidhfasdlfasdnbf");

	Header[] headers = new Header[] { header1, header2, header3 };

	Map<String, String> headerMap = defaultHttpService.createHeaderMap(headers);

	Assert.assertEquals(3, headerMap.size());
	Assert.assertTrue(headerMap.containsKey("Content-Type"));
	Assert.assertTrue(headerMap.containsKey("Content-Length"));
	Assert.assertTrue(headerMap.containsKey("Content-Length"));

    }

    private Header createHeader(final String name, final String value) {
	Header header = new Header() {

	    @Override
	    public HeaderElement[] getElements() throws ParseException {
		return new HeaderElement[] { new HeaderElement() {

		    @Override
		    public String getName() {
			// TODO Auto-generated method stub
			return name;
		    }

		    @Override
		    public NameValuePair getParameter(int arg0) {
			// TODO Auto-generated method stub
			return new NameValuePair() {

			    @Override
			    public String getValue() {
				// TODO Auto-generated method stub
				return value;
			    }

			    @Override
			    public String getName() {
				// TODO Auto-generated method stub
				return name;
			    }
			};
		    }

		    @Override
		    public NameValuePair getParameterByName(String arg0) {
			// TODO Auto-generated method stub
			return new NameValuePair() {

			    @Override
			    public String getValue() {
				// TODO Auto-generated method stub
				return value;
			    }

			    @Override
			    public String getName() {
				// TODO Auto-generated method stub
				return name;
			    }
			};
		    }

		    @Override
		    public int getParameterCount() {
			// TODO Auto-generated method stub
			return 1;
		    }

		    @Override
		    public NameValuePair[] getParameters() {
			// TODO Auto-generated method stub
			return new NameValuePair[] { new NameValuePair() {

			    @Override
			    public String getValue() {
				// TODO Auto-generated method stub
				return value;
			    }

			    @Override
			    public String getName() {
				// TODO Auto-generated method stub
				return name;
			    }
			} };
		    }

		    @Override
		    public String getValue() {
			// TODO Auto-generated method stub
			return value;
		    }
		} };
	    }

	    @Override
	    public String getName() {
		return name;
	    }

	    @Override
	    public String getValue() {
		return value;
	    }

	};
	return header;
    }

    @Test
    public void testHeaders() {
	Request request = new Request();
	request.setUrl("http://localhost:8088/test");
	request.addHeader("Content-Type", "application/json");
	request.addHeader("Auth-Token", "sdlfckjbsdafgsgdalfDSGFYgosdfgisdF");
	request.addHeader("Context", "live");

	HttpUriRequest httpRequest = defaultHttpService.createHttpRequest(request);

	Assert.assertEquals("GET", httpRequest.getMethod());
	Assert.assertEquals(3, httpRequest.getAllHeaders().length);
    }

}
