package com.hawkware.apollo.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.model.builder.impl.ServerBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationContextServiceImplTest {

    @Mock
    private GenericDAO<ApplicationContext> applicationContextDAO;

    @InjectMocks
    private ApplicationContextServiceImpl applicationContextService = new ApplicationContextServiceImpl();

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetContextString() {
	String context = "dev";
	ApplicationContext expectedContext = new ApplicationContext(context);
	Mockito.when(applicationContextDAO.get("name", context)).thenReturn(expectedContext);

	ApplicationContext actualContext = applicationContextService.getContext(context);
	Assert.assertEquals(expectedContext, actualContext);
    }

    @Test
    public void testGetContextServer() {
	String context = "dev";
	ApplicationContext expectedContext = new ApplicationContext(context);
	Server server = new ServerBuilder().hostName("localhiost").ipAddress("127.0.0.1").build();
	expectedContext.getServers().add(server);

	Map<String, Object> criteria = new HashMap<String, Object>();
	criteria.put("server.hostname", server.getHostNames().get(0));

	Mockito.when(applicationContextDAO.get(Mockito.anyMap())).thenReturn(Arrays.asList(expectedContext));

	ApplicationContext actualContext = applicationContextService.getContext(server);
	Assert.assertEquals(expectedContext, actualContext);
    }

    @Test
    public void testGetContexts() {

    }

    @Test
    public void testDeleteContext() {

    }

    @Test
    public void testSaveContext() {

    }

}
