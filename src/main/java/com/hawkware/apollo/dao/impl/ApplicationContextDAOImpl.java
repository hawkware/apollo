package com.hawkware.apollo.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.dao.query.Criterion;
import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.builder.impl.ApplicationContextBuilder;
import com.hawkware.apollo.model.builder.impl.ServerBuilder;

public class ApplicationContextDAOImpl extends GenericDAO<ApplicationContext> {
    private AtomicLong appContextIdGenerator = new AtomicLong(10001);
    private Map<String, ApplicationContext> applicationContextsByName = new HashMap<String, ApplicationContext>();
    private Map<Long, ApplicationContext> applicationContextsById = new HashMap<Long, ApplicationContext>();

    public ApplicationContextDAOImpl() {
	ApplicationContext local = new ApplicationContextBuilder().name("local")
		.server(new ServerBuilder().hostName("localhost").ipAddress("127.0.0.1").build()).build();
	save(local);

	ApplicationContext dev = new ApplicationContextBuilder().name("dev")
		.server(new ServerBuilder().hostName("api.dev.privilink.com").ipAddress("192.168.9.101").build())
		.build();
	save(dev);

	ApplicationContext qa = new ApplicationContextBuilder().name("qa")
		.server(new ServerBuilder().hostName("api.qa.privilink.com").ipAddress("192.168.9.201").build())
		.build();
	save(qa);

	ApplicationContext live = new ApplicationContextBuilder().name("live")
		.server(new ServerBuilder().hostName("api.privilink.com").ipAddress("192.168.9.301").build()).build();
	save(live);
    }

    @Override
    public Long save(ApplicationContext applicationContext) {
	if (applicationContext.getId() == null) {
	    applicationContext.setId(appContextIdGenerator.getAndIncrement());
	}
	applicationContextsByName.put(applicationContext.getName(), applicationContext);
	applicationContextsById.put(applicationContext.getId(), applicationContext);
	return applicationContext.getId();
    }

    @Override
    public ApplicationContext get(String keyName, Object keyValue) {
	return applicationContextsByName.get(keyValue);
    }

    @Override
    public ApplicationContext get(Long id) {
	return applicationContextsById.get(id);
    }

    @Override
    public Collection<ApplicationContext> get(Map<String, Criterion> criteriaMap) {
	return applicationContextsByName.values();
    }

    @Override
    public boolean delete(ApplicationContext applicationContext) {
	ApplicationContext byName = applicationContextsByName.remove(applicationContext.getName());
	ApplicationContext byId = applicationContextsById.remove(applicationContext.getId());
	return byId == byName;
    }

}
