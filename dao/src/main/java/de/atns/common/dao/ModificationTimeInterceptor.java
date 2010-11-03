package de.atns.common.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Thomas Baum
 * @since 08.01.2009 09:23:28
 */
public class ModificationTimeInterceptor extends EmptyInterceptor {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(ModificationTimeInterceptor.class);
    private static final long serialVersionUID = 5698498007655555145L;
    private int updates;
    private int creates;
    private int loads;

    //    private JspUpdateService jspUpdateService;
    private HibernateInterceptorHandler deleteHandler = null;

// --------------------- GETTER / SETTER METHODS ---------------------

    public HibernateInterceptorHandler getDeleteHandler() {
        return deleteHandler;
    }

    public void setDeleteHandler(final HibernateInterceptorHandler deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Interceptor ---------------------

//    public void setJspUpdateService(final JspUpdateService jspUpdateService) {
//        this.jspUpdateService = jspUpdateService;
//    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Interceptor ---------------------

    @Override
    public boolean onLoad(final Object entity,
                          final Serializable id,
                          final Object[] state,
                          final String[] propertyNames,
                          final Type[] types) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("@onLoad:" + entity.getClass() + "/" + id);
        }
        if (entity instanceof LongIdObject) {
            loads++;
        }
        return false;
    }

    @Override
    public boolean onFlushDirty(final Object entity,
                                final Serializable id,
                                final Object[] currentState,
                                final Object[] previousState,
                                final String[] propertyNames,
                                final Type[] types) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("@onFlushDirty:" + entity.getClass() + "/" + id);
        }
        boolean update = false;
        if (entity instanceof LongIdObject) {
            updates++;
            update |= updateProperty(currentState, propertyNames, "lastUpdateTimestamp", new Date());
        }
        return update;
    }

    @Override
    public boolean onSave(final Object entity,
                          final Serializable id,
                          final Object[] state,
                          final String[] propertyNames,
                          final Type[] types) {
        boolean update = false;
        if (LOG.isDebugEnabled()) {
            LOG.debug("@onSave:" + entity.getClass() + "/" + id);
        }
        if (entity instanceof LongIdObject) {
            creates++;
            update |= updateProperty(state, propertyNames, "createTimestamp", new Date());
            update |= updateProperty(state, propertyNames, "lastUpdateTimestamp", new Date());
        }
        return update;
    }

    @Override
    public void onDelete(final Object entity,
                         final Serializable id,
                         final Object[] state,
                         final String[] propertyNames,
                         final Type[] types) {
        if (deleteHandler != null) {
            deleteHandler.execute(entity, id, state, propertyNames, types);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("@onDelete:" + entity.getClass() + "/" + id);
        }
    }

    @Override
    public void afterTransactionCompletion(final Transaction tx) {
//        if (tx.wasCommitted()) {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug("Creations: " + creates + ", Updates: " + updates + ", Loads: " + loads);
//            }
//        }
//        if (jspUpdateService != null && (updates > 0 || creates > 0)) {
//            //TODO fix jsp-update
//            LOG.debug("trigger update jsp-fragments");
//            jspUpdateService.triggerUpdate();
//        }
        updates = 0;
        creates = 0;
        loads = 0;
    }

// -------------------------- OTHER METHODS --------------------------

    private boolean updateProperty(final Object[] state, final String[] propertyNames, final String name,
                                   final Object value) {
        for (int i = 0; i < propertyNames.length; i++) {
            if (name.equals(propertyNames[i])) {
                state[i] = value;
                return true;
            }
        }
        return false;
    }
}
