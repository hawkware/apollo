package com.hawkware.apollo.dao.query.impl;

import com.hawkware.apollo.dao.query.Criterion;

public class In extends Criterion {

    public In(String name, Object[] values) {
	super(name, values);
    }
}
