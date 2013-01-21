package com.hawkware.apollo.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.service.ContextService;

public class ApplicationContextServiceImpl implements ContextService {

    private GenericDAO<ApplicationContext> applicationContextDAO;

    @Override
    public ApplicationContext getContext(String name) {
	return applicationContextDAO.get("name", name);
    }

    @Override
    public ApplicationContext getContext(Server server) {
	Map<String, Object> criteria = new HashMap<String, Object>();

	criteria.put("server.hostName", server.getHostNames());

	Collection<ApplicationContext> contexts = getContexts(criteria);
	if (contexts != null && contexts.iterator().hasNext()) {
	    return contexts.iterator().next();
	}
	return null;
    }

    @Override
    public Collection<ApplicationContext> getContexts(Map<String, Object> criteria) {
	return applicationContextDAO.get(criteria);
    }

    @Override
    public boolean deleteContext(ApplicationContext applicationContext) {
	return applicationContextDAO.delete(applicationContext);
    }

    @Override
    public Object saveContext(ApplicationContext applicationContext) {
	return applicationContextDAO.save(applicationContext);
    }

    public void setApplicationContextDAO(GenericDAO<ApplicationContext> applicationContextDAO) {
	this.applicationContextDAO = applicationContextDAO;
    }

}
