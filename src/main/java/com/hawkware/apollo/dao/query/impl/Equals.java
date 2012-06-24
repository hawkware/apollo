package com.hawkware.apollo.dao.query.impl;

import com.hawkware.apollo.dao.query.Criterion;

public class Equals extends Criterion {

    public Equals(String name, Object value) {
	super(name, value);
    }
}
