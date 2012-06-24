/**
 * 
 */
package com.hawkware.apollo.dao;

import java.util.Collection;
import java.util.Map;

import com.hawkware.apollo.dao.query.Criterion;

/**
 * 
 * Generic DAO implementation
 * 
 * @param <T>
 *            the Object type the DAO will be responsible for
 * 
 *            $Id: GenericDAO.java,v 1.4 2011/05/11 21:55:30 frank Exp $
 */
public abstract class GenericDAO<T> {

    /**
     * 
     * @param object
     * @return
     */
    public abstract Long save(T object);

    /**
     * 
     * @param clazz
     * @param keyName
     * @param keyValue
     * @return
     */
    public abstract T get(String keyName, Object keyValue);

    /**
     * 
     * @param id
     * @return
     */
    public abstract T get(Long id);

    /**
     * 
     * @param criteriaMap
     * @return
     */
    public abstract Collection<T> get(Map<String, Criterion> criteriaMap);

    /**
     * 
     * @param object
     * @return
     */
    public abstract boolean delete(T object);

}
