package com.hawkware.apollo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

		criteria.put("servers.hostName", server.getHostName());

		Collection<ApplicationContext> contexts = getContexts(criteria);
		if (contexts != null && contexts.iterator().hasNext()) {
			return contexts.iterator().next();
		} else {
			// if hostname check fails, use ip address as well
			Map<String, Object> criteria2 = new HashMap<String, Object>();
			criteria.put("servers.ipAddress", server.getIpAddress());
			Collection<ApplicationContext> contexts2 = getContexts(criteria2);
			if (contexts2 != null && contexts2.iterator().hasNext()) {
				return contexts2.iterator().next();
			}
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

	@Override
	public Collection<Object> saveContexts(Collection<ApplicationContext> applicationContexts) {
		List<Object> ids = new ArrayList<Object>();
		for (ApplicationContext applicationContext : applicationContexts) {
			Object id = saveContext(applicationContext);
			ids.add(id);
		}
		return ids;
	}

	public void setApplicationContextDAO(GenericDAO<ApplicationContext> applicationContextDAO) {
		this.applicationContextDAO = applicationContextDAO;
	}

}
