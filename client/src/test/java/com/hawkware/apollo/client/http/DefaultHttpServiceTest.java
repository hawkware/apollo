package com.hawkware.apollo.client.http;

import org.apache.http.client.HttpClient;
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
    public void testExecute() {

    }

    @Test
    public void testExecuteInternal() {

    }

    @Test
    public void testCreateHttpRequest() {

    }

    @Test
    public void testCreateHandler() {

    }

    @Test
    public void testCreateHeaderMap() {

    }

}
