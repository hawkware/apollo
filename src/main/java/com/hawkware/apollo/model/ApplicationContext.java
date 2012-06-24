package com.hawkware.apollo.model;

import java.util.ArrayList;
import java.util.List;

public class ApplicationContext {
    private Long id;

    private String name;

    private List<Server> servers;

    public ApplicationContext() {
    }

    public ApplicationContext(String name) {
	this.name = name;
	this.servers = new ArrayList<Server>();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<Server> getServers() {
	return servers;
    }

    public void setServers(List<Server> servers) {
	this.servers = servers;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return "ApplicationContext [id=" + id + ", name=" + name + ", servers=" + servers + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((servers == null) ? 0 : servers.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ApplicationContext other = (ApplicationContext) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (servers == null) {
	    if (other.servers != null)
		return false;
	} else if (!servers.equals(other.servers))
	    return false;
	return true;
    }

}
