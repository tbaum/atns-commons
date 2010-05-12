package de.atns.common.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;

public abstract class NonReturningHibernateCallback implements HibernateCallback {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HibernateCallback ---------------------

    @Override public final Object doInHibernate(final Session session) throws HibernateException, SQLException {
        executeInHibernate(session);
        return null;
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract void executeInHibernate(final Session session) throws HibernateException, SQLException;
}
