package com.hawkware.apollo.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.model.Application;

public class ApplicationDAOImpl extends GenericDAO<Application> {

	private MongoTemplate mongoTemplate;

	public ApplicationDAOImpl() {
	}

	@Override
	public Object save(Application application) {
		mongoTemplate.save(application, Application.class.getName());
		return application.getId();
	}

	@Override
	public Application get(String key, Object value) {
		Application application = mongoTemplate.findOne(new Query(Criteria.where(key).is(value)), Application.class,
				Application.class.getName());
		return application;
	}

	@Override
	public Application get(Object id) {
		Application application = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Application.class,
				Application.class.getName());
		return application;
	}

    @Override
    public Collection<Application> get(Map<String, Object> criteriaMap) {
	List<Application> applications = new ArrayList<Application>();
	Criteria criteria = new Criteria();
	if (criteriaMap != null && !criteriaMap.isEmpty()) {
	    boolean first = true;
	    for (Entry<String, Object> entry : criteriaMap.entrySet()) {
		if (first) {
		    criteria = Criteria.where(entry.getKey()).is(entry.getValue());
		    first = false;
		    continue;
		}
		criteria.and(entry.getKey()).is(entry.getValue());
	    }
	}
	applications = mongoTemplate.find(new Query(criteria), Application.class, Application.class.getName());
	return applications;
    }

	@Override
	public boolean delete(Application application) {
		try {
			mongoTemplate.remove(new Query(Criteria.where("name").is(application.getName())),
					Application.class.getName());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
		((MappingMongoConverter) this.mongoTemplate.getConverter()).setMapKeyDotReplacement("@");
	}
}
