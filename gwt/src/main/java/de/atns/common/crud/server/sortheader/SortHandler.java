package de.atns.common.crud.server.sortheader;

import de.atns.common.crud.client.sortheader.OrderField;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static de.atns.common.crud.client.sortheader.OrderField.Sort.NONE;

/**
 * @author mwolter
 * @since 19.07.11 12:01
 */
public class SortHandler {
// ------------------------------ FIELDS ------------------------------

    private final EntityManager em;
    private final OrderField order;
    private final OrderField.Sort sort;

// --------------------------- CONSTRUCTORS ---------------------------

    public SortHandler(EntityManager em, OrderField order, OrderField.Sort sort) {
        this.em = em;
        this.order = order;
        this.sort = sort;
    }

// -------------------------- OTHER METHODS --------------------------

    public <A> Long getCount(Class<A> clazz, String where) {
        Query count = em.createQuery(
                "SELECT count(x) FROM " + clazz.getSimpleName() + " x " + (where != null ? where : ""));
        return ((Long) count.getSingleResult());
    }

    public <A> List<A> getResultList(Class<A> clazz, String where, int startentry, int pagerange) {
        pagerange = pagerange == 0 ? Integer.MAX_VALUE : pagerange;
        TypedQuery<A> query = em.createQuery(
                "SELECT x FROM " + clazz.getSimpleName() + " x " + (where != null ? where : "") + " " +
                        ((order != null && sort != null && sort != NONE) ? ("order by lower(str(x." + order.name() + ")) " + sort.name()) : "order by x.id asc"),
                clazz);
        query.setFirstResult(startentry);
        query.setMaxResults(pagerange);
        return query.getResultList();
    }
}
