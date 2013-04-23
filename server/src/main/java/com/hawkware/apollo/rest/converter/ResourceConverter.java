package com.hawkware.apollo.rest.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ResourceConverter<F, T> {
	public abstract T from(F from);

	public abstract F to(T to);

	public List<T> from(Collection<F> froms) {
		List<T> resources = new ArrayList<T>();
		for (F from : froms) {
			T to = from(from);
			if (to != null) {
				resources.add(to);
			}
		}
		return resources;
	}

	public List<F> to(Collection<T> resources) {
		List<F> froms = new ArrayList<F>();
		for (T resource : resources) {
			F property = to(resource);
			if (property != null) {
				froms.add(property);
			}
		}
		return froms;
	}
}
