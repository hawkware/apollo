package com.hawkware.apollo.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.hawkware.apollo.model.Application;
import com.hawkware.apollo.model.builder.impl.ApplicationBuilder;
import com.hawkware.apollo.model.builder.impl.PropertyBuilder;
import com.mongodb.Mongo;

import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;
import de.flapdoodle.embedmongo.runtime.Network;

public class ApplicationDAOImplTest {

    private ApplicationDAOImpl dao;

    private MongoTemplate mongoTemplate;

    private static MongodExecutable mongodExe;

    private static MongodProcess mongod;

    private static Mongo mongo;

    private static final int MONGOD_PORT = 12346;

    @BeforeClass
    public static void setUpClass() throws Exception {
	MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();
	mongodExe = runtime.prepare(new MongodConfig(Version.V2_1_0, MONGOD_PORT, Network.localhostIsIPv6()));
	mongod = mongodExe.start();
	mongo = new Mongo("localhost", MONGOD_PORT);
    }

    @Before
    public void setup() throws Exception {
	mongoTemplate = new MongoTemplate(mongo, "apollo");
	dao = new ApplicationDAOImpl();
	dao.setMongoTemplate(mongoTemplate);
	populateDB();
    }

    @After
    public void teardown() {

    }

    @AfterClass
    public static void tearDown() throws Exception {
	mongod.stop();
	mongodExe.cleanup();
    }

    @Test
    public void testSaveApplication() {

	Application expected = new ApplicationBuilder()
		.name("test-save")
		.property(
			new PropertyBuilder().name("status/^/url")
				.value("local", "http://localhost:8280/services/voxeo/callstatus")
				.value("dev", "http://api.dev.privilink.com/services/voxeo/callstatus")
				.value("qa", "http://api.qa.privilink.com/services/voxeo/callstatus")
				.value("live", "http://api.privilink.com/services/voxeo/callstatus").build())
		.property(
			new PropertyBuilder().name("scheduler/^/url").value("local", "http://localhost:8280/scheduler")
				.value("dev", "http://scheduler.dev.byteborne.com/scheduler")
				.value("qa", "http://scheduler.qa.byteborne.com/scheduler")
				.value("live", "http://scheduler.byteborne.com/scheduler").build()).build();
	dao.save(expected);

	Application actual = dao.get("name", expected.getName());

	Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetStringObject() {
	Application expected = new ApplicationBuilder()
		.name("test-save-and-get")
		.property(
			new PropertyBuilder().name("status/^/url")
				.value("local", "http://localhost:8280/services/voxeo/callstatus")
				.value("dev", "http://api.dev.privilink.com/services/voxeo/callstatus")
				.value("qa", "http://api.qa.privilink.com/services/voxeo/callstatus")
				.value("live", "http://api.privilink.com/services/voxeo/callstatus").build())
		.property(
			new PropertyBuilder().name("scheduler/^/url").value("local", "http://localhost:8280/scheduler")
				.value("dev", "http://scheduler.dev.byteborne.com/scheduler")
				.value("qa", "http://scheduler.qa.byteborne.com/scheduler")
				.value("live", "http://scheduler.byteborne.com/scheduler").build()).build();
	dao.save(expected);

	Application actual = dao.get("name", expected.getName());

	Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetObject() {
	Application expected = new ApplicationBuilder()
		.name("test-get-by-id")
		.property(
			new PropertyBuilder().name("status/^/url")
				.value("local", "http://localhost:8280/services/voxeo/callstatus")
				.value("live", "http://api.privilink.com/services/voxeo/callstatus").build())
		.property(
			new PropertyBuilder().name("scheduler/^/url").value("local", "http://localhost:8280/scheduler")
				.value("qa", "http://scheduler.qa.byteborne.com/scheduler")
				.value("live", "http://scheduler.byteborne.com/scheduler").build()).build();
	dao.save(expected);

	Application actual = dao.get(expected.getId());

	Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetMapOfStringCriterion() {
	Application expected = new ApplicationBuilder()
		.name("test-get-by-criteria")
		.property(
			new PropertyBuilder().name("status/^/url")
				.value("live", "http://api.privilink.com/services/voxeo/callstatus").build())
		.property(
			new PropertyBuilder().name("scheduler/^/url").value("local", "http://localhost:8280/scheduler")
				.value("live", "http://scheduler.byteborne.com/scheduler").build()).build();
	dao.save(expected);

	Map<String, Object> criteria = new HashMap<String, Object>();
	criteria.put("name", expected.getName());
	criteria.put("id", expected.getId());

	Collection<Application> actual = dao.get(criteria);

	Assert.assertTrue(actual.contains(expected));
    }

    @Test
    public void testDeleteApplication() {
	Application expected = new ApplicationBuilder()
		.name("test-get-by-criteria")
		.property(
			new PropertyBuilder().name("status/^/url")
				.value("live", "http://api.privilink.com/services/voxeo/callstatus").build())
		.property(
			new PropertyBuilder().name("scheduler/^/url").value("local", "http://localhost:8280/scheduler")
				.value("live", "http://scheduler.byteborne.com/scheduler").build()).build();
	dao.save(expected);

	Application actual = dao.get(expected.getId());
	Assert.assertEquals(expected, actual);

	dao.delete(expected);

	Application actual2 = dao.get(expected.getId());
	Assert.assertNull(actual2);
    }

    private void populateDB() throws Exception {
	Application corona = new ApplicationBuilder()
		.name("corona")
		.property(
			new PropertyBuilder().name("callstatus/^/url")
				.value("local", "http://localhost:8280/services/voxeo/callstatus")
				.value("dev", "http://api.dev.privilink.com/services/voxeo/callstatus")
				.value("qa", "http://api.qa.privilink.com/services/voxeo/callstatus")
				.value("live", "http://api.privilink.com/services/voxeo/callstatus").build())
		.property(
			new PropertyBuilder().name("scheduler/^/url").value("local", "http://localhost:8280/scheduler")
				.value("dev", "http://scheduler.dev.byteborne.com/scheduler")
				.value("qa", "http://scheduler.qa.byteborne.com/scheduler")
				.value("live", "http://scheduler.byteborne.com/scheduler").build()).build();
	dao.save(corona);
    }

}
