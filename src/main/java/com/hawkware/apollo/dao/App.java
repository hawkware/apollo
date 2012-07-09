package com.hawkware.apollo.dao;

import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.Mongo;

public class App {
    private static final Log log = LogFactory.getLog(App.class);

    public static void main(String[] args) {
	try {
	    MongoOperations mongoOps = new MongoTemplate(new Mongo(), "test");
	    mongoOps.insert(new Person("Joe", 34));
	    log.info(mongoOps.findOne(new Query(Criteria.where("name").is("Joe")), Person.class));
	    mongoOps.dropCollection("person");
	} catch (UnknownHostException ex) {
	    log.error(ex.getMessage());
	}
    }
}
