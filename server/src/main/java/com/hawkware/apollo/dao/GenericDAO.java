/**
 * 
 */
package com.hawkware.apollo.dao;

import java.util.Collection;
import java.util.Map;

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
    public abstract Object save(T object);

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
    public abstract T get(Object id);

    /**
     * 
     * @param criteriaMap
     * @return
     */
    public abstract Collection<T> get(Map<String, Object> criteriaMap);

    /**
     * 
     * @param object
     * @return
     */
    public abstract boolean delete(T object);

}
