package com.hawkware.apollo.model.builder.impl;

import java.util.ArrayList;
import java.util.List;

import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.model.builder.Builder;

public class ApplicationContextBuilder implements Builder<ApplicationContext> {

    private String name;
    private List<Server> servers = new ArrayList<Server>();

    @Override
    public ApplicationContext build() {
	ApplicationContext app = new ApplicationContext();
	app.setName(this.name);
	app.setServers(this.servers);
	return app;
    }

    public ApplicationContextBuilder name(String name) {
	this.name = name;
	return this;
    }

    public ApplicationContextBuilder server(Server server) {
	if (server != null) {
	    this.servers.add(server);
	}
	return this;
    }
}
