package com.hawkware.apollo.rest.converter.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.model.builder.impl.ServerBuilder;
import com.hawkware.apollo.rest.resources.ServerResource;

public class ServerResourceConverterTest {

	private ServerResourceConverter serverResourceConverter;

	@Before
	public void setUp() throws Exception {
		serverResourceConverter = new ServerResourceConverter();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFromServer() {

		Server server = new ServerBuilder().hostName("localhost").ipAddress("127.0.0.1").build();

		ServerResource resource = serverResourceConverter.to(server);

		Assert.assertEquals(server.getHostName(), resource.getHostName());
		Assert.assertEquals(server.getIpAddress(), resource.getIpAddress());

	}

	@Test
	public void testToServerResource() {

		ServerResource resource = new ServerResource();
		String hostName = "localhost";
		String ipAdddress = "127.0.0.1";

		resource.setHostName(hostName);
		resource.setIpAddress(ipAdddress);

		Server server = serverResourceConverter.from(resource);

		Assert.assertEquals(resource.getHostName(), server.getHostName());
		Assert.assertEquals(resource.getIpAddress(), server.getIpAddress());
	}

	@Test
	public void testFromCollectionOfF() {
		Server server = new ServerBuilder().hostName("localhost1").ipAddress("127.0.0.1").build();
		Server server2 = new ServerBuilder().hostName("localhost2").ipAddress("127.0.0.2").build();
		Server server3 = new ServerBuilder().hostName("localhost3").ipAddress("127.0.0.3").build();
		List<Server> servers = new ArrayList<Server>(Arrays.asList(server, server2, server3));

		List<ServerResource> resources = serverResourceConverter.to(servers);

		Assert.assertEquals(servers.size(), resources.size());
	}

	@Test
	public void testToCollectionOfT() {

		ServerResource resource1 = new ServerResource();
		String hostName1 = "localhost";
		String address1 = "127.0.0.1";

		resource1.setHostName(hostName1);
		resource1.setIpAddress(address1);

		ServerResource resource2 = new ServerResource();
		String hostName2 = "localhost";
		String address2 = "127.0.0.1";
		resource2.setHostName(hostName2);
		resource2.setIpAddress(address2);

		ServerResource resource3 = new ServerResource();
		String hostName3 = "localhost";
		String address3 = "127.0.0.1";
		resource3.setHostName(hostName3);
		resource3.setIpAddress(address3);

		List<ServerResource> resources = new ArrayList<ServerResource>(Arrays.asList(resource1, resource2, resource3));

		List<Server> servers = serverResourceConverter.from(resources);

		Assert.assertEquals(resources.size(), servers.size());
	}

}
