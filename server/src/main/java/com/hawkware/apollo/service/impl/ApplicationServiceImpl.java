package com.hawkware.apollo.service.impl;

import java.util.Collection;
import java.util.Map;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.service.ApplicationService;

public class ApplicationServiceImpl implements ApplicationService {

	private GenericDAO<Application> applicationDAO;

	@Override
	public Application getApplication(String name) {
		return applicationDAO.get("name", name);
	}

	@Override
	public Collection<Application> getApplications(Map<String, Object> criteria) {
		return applicationDAO.get(criteria);
	}

	@Override
	public boolean deleteApplication(Application application) {
		return applicationDAO.delete(application);
	}

	@Override
	public Object saveApplication(Application application) {
		return applicationDAO.save(application);
	}

	public void setApplicationDAO(GenericDAO<Application> applicationDAO) {
		this.applicationDAO = applicationDAO;
	}

}
