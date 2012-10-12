package com.hawkware.apollo.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.dao.query.Criterion;
import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.model.builder.impl.ApplicationBuilder;
import com.hawkware.apollo.model.builder.impl.PropertyBuilder;

public class ApplicationDAOImpl extends GenericDAO<Application> {

    private AtomicLong appIdGenerator = new AtomicLong(10001);
    private Map<String, Application> applicationsByName = new HashMap<String, Application>();
    private Map<Long, Application> applicationsById = new HashMap<Long, Application>();

    public ApplicationDAOImpl() {
	Application corona = new ApplicationBuilder()
		.name("corona")
		.property(
			new PropertyBuilder().name("callstatus.url")
				.value("local", "http://localhost:8280/services/voxeo/callstatus")
				.value("dev", "http://api.dev.privilink.com/services/voxeo/callstatus")
				.value("qa", "http://api.qa.privilink.com/services/voxeo/callstatus")
				.value("live", "http://api.privilink.com/services/voxeo/callstatus").build())
		.property(
			new PropertyBuilder().name("scheduler.url").value("local", "http://localhost:8280/scheduler")
				.value("dev", "http://scheduler.dev.byteborne.com/scheduler")
				.value("qa", "http://scheduler.qa.byteborne.com/scheduler")
				.value("live", "http://scheduler.byteborne.com/scheduler").build()).build();
	save(corona);
    }

    @Override
    public Object save(Application application) {
	if (application.getId() == null) {
	    application.setId(appIdGenerator.getAndIncrement());
	}
	applicationsByName.put(application.getName(), application);
	applicationsById.put(application.getId(), application);
	return application.getId();
    }

    @Override
    public Application get(String keyName, Object keyValue) {
	return applicationsByName.get(keyValue);
    }

    @Override
    public Application get(Object id) {
	return applicationsById.get(id);
    }

    @Override
    public Collection<Application> get(Map<String, Criterion> criteriaMap) {
	return applicationsByName.values();
    }

    @Override
    public boolean delete(Application application) {
	Application byName = applicationsByName.remove(application.getName());
	Application byId = applicationsById.remove(application.getId());
	return byId == byName;
    }

}
