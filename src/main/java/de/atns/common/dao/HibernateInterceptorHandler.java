package de.atns.common.dao;

import org.hibernate.type.Type;

import java.io.Serializable;

/**
 * @author Thomas Baum
 * @since 20.02.2009 20:03:48
 */
public interface HibernateInterceptorHandler {

    void execute(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types);
}
