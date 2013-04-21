package de.atns.common.crud.server.sortheader;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import de.atns.common.crud.client.sortheader.OrderField;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.List;

import static de.atns.common.crud.client.sortheader.OrderField.Sort;
import static de.atns.common.crud.client.sortheader.OrderField.Sort.NONE;
import static java.lang.Integer.MAX_VALUE;

/**
 * @author mwolter
 * @since 19.07.11 12:01
 */
public class SortHandler {

    private final Provider<EntityManager> em;

    @Inject public SortHandler(Provider<EntityManager> em) {
        this.em = em;
    }

    public <A> Long getCount(Class<A> clazz, String where) {
        Query count = em.get().createQuery(
                "SELECT count(*) FROM " + clazz.getSimpleName() + " " + (where != null ? ("where " + where) : ""));
        return ((Long) count.getSingleResult());
    }

    @Transactional
    public <A> List<A> getResultList(Class<A> clazz, String where, int startentry, int pagerange, OrderField order,
                                     Sort sort) {
        pagerange = pagerange == 0 ? MAX_VALUE : pagerange;
        StringBuilder queryString = new StringBuilder();
        queryString.append("FROM ").append(clazz.getSimpleName()).append(" ");

        if (where != null) {
            queryString.append(" where ").append(where).append(" ");
        }

        if (order != null && sort != null && sort != NONE) {
            EntityType<A> entity = em.get().getMetamodel().entity(clazz);
            Attribute<? super A, ?> attribute = entity.getAttribute(order.name());
            queryString.append(" order by ");
            if (attribute.getJavaType().equals(String.class)) {
                queryString.append(" lower(").append(order.name()).append(") ");
            } else {
                queryString.append(order.name()).append(" ");
            }
            queryString.append(sort.name());
        } else {
            queryString.append(" order by id asc");
        }

        TypedQuery<A> query = em.get().createQuery(queryString.toString(), clazz);
        query.setFirstResult(startentry);
        query.setMaxResults(pagerange);
        return query.getResultList();
    }
}
