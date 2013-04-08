package com.hawkware.apollo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
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
import com.hawkware.apollo.model.Application;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceImplTest {

	@Mock
	private GenericDAO<Application> applicationDAO;

	@InjectMocks
	private ApplicationServiceImpl applicationService = new ApplicationServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetApplication() {
		String appName = "apollo";
		Application expectedApp = new Application();
		Mockito.when(applicationDAO.get("name", appName)).thenReturn(expectedApp);

		Application actualApp = applicationService.getApplication("apollo");
		Assert.assertEquals(expectedApp, actualApp);
	}

	@Test
	public void testGetApplications() {
		Map<String, Object> criteria = new HashMap<String, Object>();
		Collection<Application> expectedAppsList = new ArrayList<Application>();

		Mockito.when(applicationDAO.get(criteria)).thenReturn(expectedAppsList);

		Collection<Application> actualAppsList = applicationService.getApplications(criteria);
		Assert.assertEquals(expectedAppsList.size(), actualAppsList.size());
	}

	@Test
	public void testDeleteApplicationSuccess() {
		boolean expectedOutcome = true;
		String appName = "apollo";
		Application expectedApp = new Application();
		Mockito.when(applicationDAO.get("name", appName)).thenReturn(expectedApp);

		Mockito.when(applicationDAO.delete(expectedApp)).thenReturn(expectedOutcome);

		boolean actualOutcome = applicationService.deleteApplication(expectedApp);
		Assert.assertEquals(expectedOutcome, actualOutcome);
	}

	@Test
	public void testDeleteApplicationFail() {
		boolean expectedOutcome = false;
		String appName = "apollo";
		Application expectedApp = new Application();
		Mockito.when(applicationDAO.get("name", appName)).thenReturn(expectedApp);

		Mockito.when(applicationDAO.delete(expectedApp)).thenReturn(expectedOutcome);

		boolean actualOutcome = applicationService.deleteApplication(expectedApp);
		Assert.assertEquals(expectedOutcome, actualOutcome);
	}

	@Test
	public void testSaveApplication() {
		Long expectedId = Long.valueOf(10);
		Application expectedApp = new Application();
		Mockito.when(applicationDAO.save(expectedApp)).thenReturn(expectedId);

		Object actualId = applicationService.saveApplication(expectedApp);
		Assert.assertEquals(expectedId, actualId);
	}

}
