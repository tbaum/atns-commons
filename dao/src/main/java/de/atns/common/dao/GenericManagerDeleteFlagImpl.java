package de.atns.common.dao;

import de.atns.common.exception.DeletionFailedException;
import de.atns.common.filter.DeleteFlagAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public abstract class GenericManagerDeleteFlagImpl<TYPE extends LongIdObject & DeleteFlagAware> extends
        GenericManagerImpl<TYPE> implements GenericManager<TYPE> {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface GenericManager ---------------------

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteObject(final TYPE object) throws DeletionFailedException {
        if (!object.isNew()) {
            object.setDeleted(true);
            merge(object);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Collection<TYPE> loadAll() {
        enableDeleteFilter();
        return super.loadAll();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Collection<TYPE> loadAll(final Date lastModifiedTimestamp) {
        enableDeleteFilter();
        return super.loadAll(lastModifiedTimestamp);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public PartResult<TYPE> loadAll(final Map<String, Object> filter, final int start, final int max, final String sort, final boolean asc) {
        enableDeleteFilter();
        return super.loadAll(filter, start, max, sort, asc);
    }

// -------------------------- OTHER METHODS --------------------------

    protected void enableDeleteFilter() {
        getHibernateFilter("deleteFilter").setParameter("deleted", false);
    }
}
