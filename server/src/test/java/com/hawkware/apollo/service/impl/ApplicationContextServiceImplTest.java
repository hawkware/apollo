package com.hawkware.apollo.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.model.builder.impl.ApplicationContextBuilder;
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
	public void testGetContextServerByIpAddress() {
		String context = "dev";
		ApplicationContext expectedContext = new ApplicationContext(context);
		Server server = new ServerBuilder().hostName("localhiost").ipAddress("127.0.0.1").build();
		expectedContext.getServers().add(server);

		Mockito.when(applicationContextDAO.get(Matchers.anyMap())).thenReturn(null, Arrays.asList(expectedContext));

		ApplicationContext actualContext = applicationContextService.getContext(server);
		Assert.assertEquals(expectedContext, actualContext);
	}

	@Test
	public void testGetContextServerByHostName() {
		String context = "dev";
		ApplicationContext expectedContext = new ApplicationContext(context);
		Server server = new ServerBuilder().hostName("localhiost").ipAddress("127.0.0.1").build();
		expectedContext.getServers().add(server);

		Mockito.when(applicationContextDAO.get(Matchers.anyMap())).thenReturn(Arrays.asList(expectedContext));

		ApplicationContext actualContext = applicationContextService.getContext(server);
		Assert.assertEquals(expectedContext, actualContext);
	}

	@Test
	public void testGetContexts() {
		Collection<ApplicationContext> expectedContext = new ArrayList<ApplicationContext>();
		expectedContext.add(new ApplicationContextBuilder().name("dev")
				.server(new ServerBuilder().hostName("localhost").ipAddress("127.0.0.1").build()).build());
		expectedContext.add(new ApplicationContextBuilder().name("uat")
				.server(new ServerBuilder().hostName("uatserver").ipAddress("127.1.0.1").build()).build());

		Map<String, Object> criteria = new HashMap<String, Object>();

		Mockito.when(applicationContextDAO.get(criteria)).thenReturn(expectedContext);

		Collection<ApplicationContext> actualContexts = applicationContextService.getContexts(criteria);

		Assert.assertEquals(expectedContext.size(), actualContexts.size());
		Assert.assertEquals(expectedContext.iterator().next(), actualContexts.iterator().next());
	}

	@Test
	public void testDeleteContextSuccess() {
		ApplicationContext expectedContext = new ApplicationContext("dev");
		boolean expectedResult = true;

		Mockito.when(applicationContextDAO.delete(expectedContext)).thenReturn(expectedResult);
		boolean actualResult = applicationContextService.deleteContext(expectedContext);

		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testDeleteContextFailed() {
		ApplicationContext expectedContext = new ApplicationContext("dev");
		boolean expectedResult = false;

		Mockito.when(applicationContextDAO.delete(expectedContext)).thenReturn(expectedResult);
		boolean actualResult = applicationContextService.deleteContext(expectedContext);

		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testSaveContext() {
		ApplicationContext contextToSave = new ApplicationContext("dev");
		String expectedId = "somerandomhex";
		Mockito.when(applicationContextDAO.save(contextToSave)).thenReturn(expectedId);
		Object actualId = applicationContextService.saveContext(contextToSave);
		Assert.assertEquals(expectedId, actualId);
	}

}
