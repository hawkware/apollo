package com.hawkware.apollo.client.services;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.hawkware.apollo.client.http.HttpService;
import com.hawkware.apollo.client.http.Request;
import com.hawkware.apollo.client.http.Response;

public class PropertyServiceTest {

    private PropertyService propertyService;

    private HttpService mockHttpService;

    private final String payload = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><property name=\"mongodb.url\" timeToLive=\"86400\"><value context=\"qa\">http://api.test.hawkware.com</value></property>";

    @Before
    public void setUp() throws Exception {
	propertyService = new PropertyService();
	mockHttpService = Mockito.mock(HttpService.class);
	propertyService.setHttpService(mockHttpService);
    }

    @Test
    public void testGetPropertyExists() {
	String expected = "http://api.test.hawkware.com";
	Response response = new Response();
	response.setPayload(payload);
	Mockito.when(mockHttpService.execute((Request) Mockito.anyObject())).thenReturn(response);

	String actual = propertyService.getProperty("mongodb.url");

	assertEquals(expected, actual);
    }

    @Test
    public void testGetPropertyNotExists() {
	String expected = null;
	Response response = new Response();
	response.setPayload(null);
	Mockito.when(mockHttpService.execute((Request) Mockito.anyObject())).thenReturn(response);

	String actual = propertyService.getProperty("something");

	assertEquals(expected, actual);
    }

    public static void main(String[] args) throws JAXBException {
	PropertyService ps = new PropertyService();
	ps.setServerUrl("http://localhost:8184/apollo");
	ps.setApplication("apollo");
	ps.setContext("qa");
	String resp = ps.getProperty("mongodb.url");
	System.out.println(resp);
    }
}
