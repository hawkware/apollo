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
import com.hawkware.apollo.rest.resources.HostNameResource;
import com.hawkware.apollo.rest.resources.IpAddressResource;
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

	ServerResource resource = serverResourceConverter.from(server);

	Assert.assertEquals(server.getHostNames().get(0), resource.getHostNames().get(0).getValue());
	Assert.assertEquals(server.getIpAddresses().get(0), resource.getIpAddresses().get(0).getValue());

    }

    @Test
    public void testToServerResource() {

	ServerResource resource = new ServerResource();
	HostNameResource hostNameResource = new HostNameResource("localhost");
	IpAddressResource addressResource = new IpAddressResource("127.0.0.1");

	resource.getHostNames().add(hostNameResource);
	resource.getIpAddresses().add(addressResource);

	Server server = serverResourceConverter.to(resource);

	Assert.assertEquals(resource.getHostNames().get(0).getValue(), server.getHostNames().get(0));
	Assert.assertEquals(resource.getIpAddresses().get(0).getValue(), server.getIpAddresses().get(0));
    }

    @Test
    public void testFromCollectionOfF() {
	Server server = new ServerBuilder().hostName("localhost1").ipAddress("127.0.0.1").build();
	Server server2 = new ServerBuilder().hostName("localhost2").ipAddress("127.0.0.2").build();
	Server server3 = new ServerBuilder().hostName("localhost3").ipAddress("127.0.0.3").build();
	List<Server> servers = new ArrayList<Server>(Arrays.asList(server, server2, server3));

	List<ServerResource> resources = serverResourceConverter.from(servers);

	Assert.assertEquals(servers.size(), resources.size());
    }

    @Test
    public void testToCollectionOfT() {

	ServerResource resource1 = new ServerResource();
	HostNameResource hostNameResource1 = new HostNameResource("localhost");
	IpAddressResource addressResource1 = new IpAddressResource("127.0.0.1");
	resource1.getHostNames().add(hostNameResource1);
	resource1.getIpAddresses().add(addressResource1);

	ServerResource resource2 = new ServerResource();
	HostNameResource hostNameResource2 = new HostNameResource("localhost");
	IpAddressResource addressResource2 = new IpAddressResource("127.0.0.1");
	resource2.getHostNames().add(hostNameResource2);
	resource2.getIpAddresses().add(addressResource2);

	ServerResource resource3 = new ServerResource();
	HostNameResource hostNameResource3 = new HostNameResource("localhost");
	IpAddressResource addressResource3 = new IpAddressResource("127.0.0.1");
	resource3.getHostNames().add(hostNameResource3);
	resource3.getIpAddresses().add(addressResource3);

	List<ServerResource> resources = new ArrayList<ServerResource>(Arrays.asList(resource1, resource2, resource3));

	List<Server> servers = serverResourceConverter.to(resources);

	Assert.assertEquals(resources.size(), servers.size());
    }

}
