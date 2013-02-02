package com.hawkware.apollo.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.builder.impl.ApplicationContextBuilder;
import com.hawkware.apollo.model.builder.impl.ServerBuilder;
import com.mongodb.Mongo;

import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;
import de.flapdoodle.embedmongo.runtime.Network;

public class ApplicationContextDAOImplTest {

    private ApplicationContextDAOImpl dao;

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
    public void setup() throws IOException {
	mongoTemplate = new MongoTemplate(mongo, "apollo");
	dao = new ApplicationContextDAOImpl();
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
    public void testSaveApplicationContext() {
	ApplicationContext expected = new ApplicationContextBuilder().name("test-save")
		.server(new ServerBuilder().hostName("localhost").ipAddress("127.0.0.1").build()).build();

	dao.save(expected);

	ApplicationContext actual = dao.get("name", expected.getName());
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetStringObject() {
	ApplicationContext expected = new ApplicationContextBuilder().name("test-get")
		.server(new ServerBuilder().hostName("localhost").ipAddress("127.0.0.1").build()).build();

	dao.save(expected);

	ApplicationContext actual = dao.get("name", expected.getName());
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetObject() {
	ApplicationContext expected = new ApplicationContextBuilder().name("test-save-and-get")
		.server(new ServerBuilder().hostName("localhost").ipAddress("127.0.0.1").build()).build();

	dao.save(expected);

	ApplicationContext actual = dao.get(expected.getId());
	Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetMapOfStringCriterion() {
	ApplicationContext expected = new ApplicationContextBuilder().name("test-save-with-criteria")
		.server(new ServerBuilder().hostName("localhost").ipAddress("127.0.0.1").build()).build();

	dao.save(expected);
	Map<String, Object> criteria = new HashMap<String, Object>();
	// criteria.put("id", expected.getId());
	criteria.put("servers.hostName", "localhost");

	Collection<ApplicationContext> actual = dao.get(criteria);
	Assert.assertTrue(actual.contains(expected));
    }

    @Test
    public void testDeleteApplicationContext() {
	ApplicationContext expected = new ApplicationContextBuilder().name("test-save-and-delete")
		.server(new ServerBuilder().hostName("localhost").ipAddress("127.0.0.1").build()).build();

	dao.save(expected);

	ApplicationContext actual = dao.get(expected.getId());
	Assert.assertEquals(expected, actual);

	dao.delete(expected);

	ApplicationContext actual2 = dao.get(expected.getId());
	Assert.assertNull(actual2);
    }

    @SuppressWarnings({ "unchecked" })
    public void testSaveMapWithACollectionAsValue() {
	Map<String, Object> keyValues = new HashMap<String, Object>();
	keyValues.put("string", "hello");
	List<String> list = new ArrayList();
	list.add("ping");
	list.add("pong");
	keyValues.put("list", list);

	mongoTemplate.save(keyValues, "maps");

	List<Map> keyValuesFromMongo = (List<Map>) mongoTemplate.findAll(Map.class, "maps");
	Assert.assertEquals(keyValues.size(), keyValuesFromMongo.get(0).size());
	System.out.println(keyValuesFromMongo.get(0));
	Assert.assertEquals(keyValues.get("string"), keyValuesFromMongo.get(0).get("string"));
	Assert.assertTrue(List.class.isAssignableFrom(keyValuesFromMongo.get(0).get("list").getClass()));
	List<String> listFromMongo = (List) keyValuesFromMongo.get(0).get("list");
	Assert.assertEquals(list.size(), listFromMongo.size());
	Assert.assertEquals(list.get(0), listFromMongo.get(0));
	Assert.assertEquals(list.get(1), listFromMongo.get(1));
    }

    private void populateDB() {
	ApplicationContext local = new ApplicationContextBuilder().name("local")
		.server(new ServerBuilder().hostName("localhost").ipAddress("127.0.0.1").build()).build();

	dao.save(local);

	ApplicationContext dev = new ApplicationContextBuilder().name("dev")
		.server(new ServerBuilder().hostName("api.dev.privilink.com").ipAddress("192.168.9.101").build())
		.build();
	dao.save(dev);

	ApplicationContext qa = new ApplicationContextBuilder().name("qa")
		.server(new ServerBuilder().hostName("api.qa.privilink.com").ipAddress("192.168.9.201").build())
		.build();

	dao.save(qa);

	ApplicationContext live = new ApplicationContextBuilder().name("live")
		.server(new ServerBuilder().hostName("api.privilink.com").ipAddress("192.168.9.301").build()).build();
	dao.save(live);
    }

}
