package com.hawkware.apollo.dao.query.impl;

import java.util.Map;

import com.hawkware.apollo.dao.query.Criterion;

public class CriteriaBuilder {

    public CriteriaBuilder and(String field, Object value) {
	return this;
    }

    public CriteriaBuilder or(String field, Object value) {
	return this;
    }

    public CriteriaBuilder in(String field, Object[] value) {
	return this;
    }

    Map<String, Criterion> build() {
	return null;
    }
}
