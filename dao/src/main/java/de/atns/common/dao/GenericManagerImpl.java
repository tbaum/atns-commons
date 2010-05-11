package de.atns.common.dao;

import de.atns.common.exception.DeletionFailedException;
import de.atns.common.lock.LockManager;
import de.atns.common.lock.LockState;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static de.atns.common.util.ConstructorRef.lookupContructor;
import static org.hibernate.FetchMode.JOIN;
import static org.hibernate.criterion.DetachedCriteria.forClass;
import static org.hibernate.criterion.Restrictions.eq;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

public abstract class GenericManagerImpl<TYPE extends LongIdObject> extends ExtendedHibernateDaoSupport implements GenericManager<TYPE> {
// ------------------------------ FIELDS ------------------------------

    @Autowired(required = true) @Qualifier("lockManager")
    private LockManager<TYPE> lockManager;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------

    @Override @Transactional(readOnly = true, propagation = REQUIRES_NEW)
    public TYPE convert(final Long from) {
        return loadReadonly(from);
    }

// --------------------- Interface GenericManager ---------------------

    @Override public TYPE createObject() {
        return lookupContructor(getDataClass()).createInstance();
    }

    @Override @Transactional(propagation = Propagation.REQUIRED)
    public void deleteObject(final TYPE object) throws DeletionFailedException {
        createLock(object);
        getHibernateTemplate().delete(object);
    }

    @Override @Transactional(readOnly = true, propagation = REQUIRES_NEW)
    public Collection<TYPE> loadAll() {
        return findByCriteria(forClass(getDataClass()));
    }

    @Override @Transactional(readOnly = true, propagation = REQUIRES_NEW)
    public Collection<TYPE> loadAll(final Date lastModifiedTimestamp) {
        return findByCriteria(
                forClass(getDataClass()).add(Restrictions.ge("lastUpdateTimestamp", lastModifiedTimestamp)));
    }

    @Override @Transactional(readOnly = true, propagation = REQUIRES_NEW)
    public PartResult<TYPE> loadAll(final Map<String, Object> filter, final int start, final int max, final String sort,
                                    final boolean asc) {
        final Class<? extends TYPE> dataClass = GenericManagerImpl.this.getDataClass();
        final PartResult<TYPE> result = executeCallback(new HibernateCallback() {
            @Override public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                final PartResult<TYPE> partResult = loadAll(session, filter, start, max, sort, asc, dataClass);


                final List<TYPE> result = loadAllHandleList(partResult.getItems(), session);
                return PartResult.createPartResult(partResult, result);
            }
        });
        return result;
    }

    @Override @Transactional(readOnly = true, propagation = REQUIRES_NEW)
    public TYPE loadReadonly(final long id) {
        final TYPE result = load(getDataClass(), id);
        getHibernateTemplate().initialize(result);
        return result;
    }

    @Override @Transactional(readOnly = true, propagation = REQUIRES_NEW)
    public TYPE loadReadonly(final long id, final String... detachedFields) {
        final DetachedCriteria criteria = forClass(getDataClass()).add(eq("id", id));
        for (final String field : detachedFields) {
            criteria.setFetchMode(field, JOIN);
        }
        final TYPE result = (TYPE) loadByCriteria(criteria);
        getHibernateTemplate().initialize(result);
        return result;
    }

    @Override @Transactional(propagation = Propagation.REQUIRED)
    public TYPE loadWriteable(final long id) {
        final TYPE result = load(getDataClass(), id);
        getHibernateTemplate().initialize(result);
        return result;
    }

    @Override @Transactional(propagation = Propagation.REQUIRED)
    public TYPE merge(final TYPE object) {
        createLock(object);
        return (TYPE) getHibernateTemplate().merge(object);
    }

    @Override public void reattach(final LongIdObject object) {
        getHibernateTemplate().lock(object, LockMode.NONE);
    }

// --------------------- Interface LockManager ---------------------

    @Override public LockState createLock(final TYPE object) {
        return lockManager.createLock(object);
    }

    @Override public void freeLock(final LockState object) {
        lockManager.freeLock(object);
    }

    @Override public LockState updateLock(final LockState object) {
        return lockManager.updateLock(object);
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract Class<? extends TYPE> getDataClass();

    protected List<TYPE> loadAllHandleList(final List<TYPE> list, final Session session) {
        return list;
    }

    protected TYPE loadUnique(final Criterion simpleExpression) {
        return (TYPE) loadByCriteria(forClass(getDataClass()).add(simpleExpression));
    }
}

