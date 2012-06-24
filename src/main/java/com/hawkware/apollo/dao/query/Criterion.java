package com.hawkware.apollo.dao.query;

import java.util.Arrays;

public abstract class Criterion {

    protected String name;

    protected Object value;

    protected Object[] values;

    public Criterion(String name, Object value) {
	this.name = name;
	this.value = value;
    }

    public Criterion(String name, Object[] values) {
	this.name = name;
	this.values = values;
    }

    public Object getValue() {
	return value;
    }

    public Object[] getValues() {
	return values;
    }

    public void setValue(Object value) {
	this.value = value;
    }

    public void setValues(Object[] values) {
	this.values = values;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((value == null) ? 0 : value.hashCode());
	result = prime * result + Arrays.hashCode(values);
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
	Criterion other = (Criterion) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (value == null) {
	    if (other.value != null)
		return false;
	} else if (!value.equals(other.value))
	    return false;
	if (!Arrays.equals(values, other.values))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Criterion [name=" + name + ", value=" + value + ", values=" + Arrays.toString(values) + "]";
    }

}
