package com.hawkware.apollo.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.hawkware.apollo.dao.GenericDAO;
import com.hawkware.apollo.model.ApplicationContext;

public class ApplicationContextDAOImpl extends GenericDAO<ApplicationContext> {

    private MongoTemplate mongoTemplate;

    public ApplicationContextDAOImpl() {
    }

    @Override
    public Object save(ApplicationContext context) {
	mongoTemplate.save(context, ApplicationContext.class.getName());
	return context.getId();
    }

    @Override
    public ApplicationContext get(String key, Object value) {
	ApplicationContext context = mongoTemplate.findOne(new Query(Criteria.where(key).is(value)),
		ApplicationContext.class, ApplicationContext.class.getName());

	return context;
    }

    @Override
    public ApplicationContext get(Object id) {
	ApplicationContext context = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
		ApplicationContext.class, ApplicationContext.class.getName());

	return context;
    }

    @Override
    public Collection<ApplicationContext> get(Map<String, Object> criteriaMap) {
	List<ApplicationContext> contexts = new ArrayList<ApplicationContext>();
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
	contexts = mongoTemplate
		.find(new Query(criteria), ApplicationContext.class, ApplicationContext.class.getName());
	return contexts;
    }

    @Override
    public boolean delete(ApplicationContext context) {
	try {
	    mongoTemplate.remove(new Query(Criteria.where("name").is(context.getName())),
		    ApplicationContext.class.getName());
	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
	this.mongoTemplate = mongoTemplate;
    }

}
