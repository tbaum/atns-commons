package de.atns.common.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hibernate.criterion.Projections.*;
import static org.hibernate.criterion.Restrictions.*;

/**
 * @author Thomas Baum
 * @since 12.09.2008
 */
public class ExtendedHibernateDaoSupport extends HibernateDaoSupport {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(ExtendedHibernateDaoSupport.class);

// -------------------------- OTHER METHODS --------------------------

    @SuppressWarnings({"unchecked"})
    protected <TYPE> TYPE executeCallback(final HibernateCallback callback) {
        return (TYPE) getHibernateTemplate().execute(callback);
    }

    @SuppressWarnings({"unchecked"})
    protected <TYPE> List<TYPE> executeFind(final HibernateCallback callback) {
        return getHibernateTemplate().executeFind(callback);
    }

    @SuppressWarnings({"unchecked"})
    protected <TYPE> List<TYPE> findByCriteria(final DetachedCriteria detachedCriteria) {
        return (List<TYPE>) getHibernateTemplate().execute(new HibernateCallback() {
            @Override public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                final Criteria criteria = detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .getExecutableCriteria(session);
                //criteria.setCacheable(true);
                return criteria.list();
            }
        });
    }

    protected <T> T get(final Session session, final Class<T> clazz, final long id) {
        return (T) session.get(clazz, id);
    }

    public Filter getHibernateFilter(final String filterName) {
        return getHibernateTemplate().enableFilter(filterName);
    }

    @SuppressWarnings({"unchecked"})
    protected <TYPE> TYPE load(final Class<? extends TYPE> dataClass, final long id) {
        //   Hibernate.initialize(result);
        return (TYPE) getHibernateTemplate().load(dataClass, id);
    }

    protected <TYPE extends Serializable> PartResult<TYPE> loadAll(final Session session,
                                                                   final Map<String, Object> filter, final int start,
                                                                   final int max, final String sort, final boolean asc,
                                                                   final Class<? extends TYPE> dataClass) {
        final Criteria criteria = createFilteredListCriteria(session, filter, dataClass);
        criteria.setProjection(Projections.countDistinct("id"));

        final Number count = (Number) criteria.uniqueResult();

        criteria.setFirstResult(start);
        criteria.setMaxResults(max);
        criteria.addOrder(asc ? Order.asc(sort) : Order.desc(sort));

        criteria.setProjection(
                distinct(
                        projectionList()
                                .add(property("id"))
                                .add(property(sort))));

        final List<Object[]> res = criteria.list();
        final List<Long> ids = new ArrayList<Long>(res.size());
        for (final Object[] re : res) {
            ids.add((Long) re[0]);
        }
        final List<TYPE> results;
        if (ids.size() > 0) {
            final Criteria criteria3 = createFilteredListCriteria(session, filter, dataClass);
            criteria3.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria3.addOrder(asc ? Order.asc(sort) : Order.desc(sort));

            criteria3.addOrder(asc ? Order.asc(sort) : Order.desc(sort));
            criteria3.add(in("id", ids));

            results = criteria3.list();
        } else {
            results = new ArrayList<TYPE>();
        }


        return PartResult.createPartResult(start, count.intValue(), results);
    }

    protected <TYPE extends Serializable> Criteria createFilteredListCriteria(final Session session,
                                                                              final Map<String, Object> filter,
                                                                              final Class<? extends TYPE> dataClass) {
        final Criteria criteria = session.createCriteria(dataClass);

        Criterion filterCriterion = null;
        for (final Map.Entry<String, Object> e : filter.entrySet()) {
            final Criterion expression;
            if (e.getValue() instanceof Criterion) {
                expression = (Criterion) e.getValue();
            } else if (e.getValue() instanceof Number) {
                expression = eq(e.getKey(), e.getValue());
            } else if (e.getValue() instanceof Boolean) {
                expression = eq(e.getKey(), e.getValue());
            } else {
                expression = ilike(e.getKey(), String.valueOf(e.getValue()), MatchMode.ANYWHERE);
            }
            filterCriterion = filterCriterion != null ? or(filterCriterion, expression) : expression;
        }

        if (filterCriterion != null) {
            criteria.add(filterCriterion);
        }

        return criteria;
    }

    @SuppressWarnings({"unchecked"})
    protected <TYPE> TYPE loadByCriteria(final DetachedCriteria detachedCriteria) {
        return (TYPE) getHibernateTemplate().execute(new HibernateCallback() {
            @Override public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                final Criteria criteria = detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                        .getExecutableCriteria(session);
                //criteria.setCacheable(true);
                return criteria.uniqueResult();
            }
        });
        /*final List<TYPE> r = findByCriteria(detachedCriteria);
        final int min = r.size();
        if (min == 0) throw new ObjectRetrievalFailureException(detachedCriteria.getClass(), detachedCriteria);
        if (min > 1)
            throw new ObjectRetrievalFailureException(detachedCriteria.getClass(), "more-than one object found for: " +
                    detachedCriteria);
        return r.get(0);              */
    }

    protected <TYPE> TYPE refresh(final TYPE type) {
        getHibernateTemplate().refresh(type);
        return type;
    }

    @Autowired(required = true)
    public void setFactory(@Qualifier("sessionFactory") final SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
