package com.hawkware.apollo.service;

import java.util.Collection;
import java.util.Map;

import com.hawkware.apollo.dao.query.Criterion;
import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;

public interface ContextService {
    public ApplicationContext getContext(String name);

    public ApplicationContext getContext(Server name);

    public Collection<ApplicationContext> getContexts(Map<String, Criterion> criteria);

    public boolean deleteContext(ApplicationContext applicationContext);

    public Object saveContext(ApplicationContext applicationContext);
}
