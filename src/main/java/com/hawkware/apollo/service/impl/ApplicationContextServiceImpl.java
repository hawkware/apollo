package com.hawkware.apollo.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.dao.query.Criterion;
import com.hawkware.apollo.dao.query.impl.In;
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
	Map<String, Criterion> criteria = new HashMap<String, Criterion>();

	criteria.put("server.hostName", new In("server.hostName", server.getHostNames().toArray()));

	Collection<ApplicationContext> contexts = getContexts(criteria);
	if (contexts != null && contexts.iterator().hasNext()) {
	    return contexts.iterator().next();
	}
	return null;
    }

    @Override
    public Collection<ApplicationContext> getContexts(Map<String, Criterion> criteria) {
	return applicationContextDAO.get(criteria);
    }

    @Override
    public boolean deleteContext(ApplicationContext applicationContext) {
	return applicationContextDAO.delete(applicationContext);
    }

    @Override
    public long saveContext(ApplicationContext applicationContext) {
	return applicationContextDAO.save(applicationContext);
    }

    public void setApplicationContextDAO(GenericDAO<ApplicationContext> applicationContextDAO) {
	this.applicationContextDAO = applicationContextDAO;
    }

}
