package com.hawkware.apollo.client;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hawkware.apollo.client.http.HttpService;
import com.hawkware.apollo.client.http.Request;
import com.hawkware.apollo.client.http.Response;
import com.hawkware.apollo.client.model.Property;
import com.hawkware.apollo.client.spring.ApolloPropertyPlaceholderConfigurer;

public class PropertyClientTest {

    private PropertyClient propertyClient;

    private HttpService mockHttpService;

    private final String payload = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><property name=\"mongodb.url\" timeToLive=\"86400\"><value context=\"qa\">http://api.test.hawkware.com</value></property>";

    @Before
    public void setUp() throws Exception {
	propertyClient = new PropertyClient();
	mockHttpService = Mockito.mock(HttpService.class);
	propertyClient.setHttpService(mockHttpService);
    }

    @Test
    public void testGetPropertyExists() {
	String expected = "http://api.test.hawkware.com";
	Response response = new Response();
	response.setPayload(payload);
	Mockito.when(mockHttpService.execute((Request) Mockito.anyObject())).thenReturn(response);

	String actual = propertyClient.getProperty("mongodb.url");

	assertEquals(expected, actual);
    }

    @Test
    public void testGetPropertyNotExists() {
	String expected = null;
	Response response = new Response();
	response.setPayload(null);
	Mockito.when(mockHttpService.execute((Request) Mockito.anyObject())).thenReturn(response);

	String actual = propertyClient.getProperty("something");

	assertEquals(expected, actual);
    }

    public static void main(String[] args) throws JAXBException {

	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-environment-config.xml");
	ApolloPropertyPlaceholderConfigurer appc = (ApolloPropertyPlaceholderConfigurer) ctx.getBean("appc");

	String test = (String) ctx.getBean("test");

	PropertyClient ps = new PropertyClient();
	ps.setServerUrl("http://localhost:8080/apollo");
	ps.setApplication("sample-app");
	ps.setContext("dev");
	String resp = ps.getProperty("admin.email");
	List<Property> props = ps.getProperties();
	System.out.println(props);
	System.out.println(resp);
    }
}
