package com.hawkware.apollo.service;

import java.util.Collection;
import java.util.Map;

import com.hawkware.apollo.model.Application;

public interface ApplicationService {
    public Application getApplication(String name);

    public Collection<Application> getApplications(Map<String, Object> criteria);

    public boolean deleteApplication(Application application);

    public Object saveApplication(Application application);

}
