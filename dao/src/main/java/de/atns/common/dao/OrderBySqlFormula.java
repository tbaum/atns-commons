package de.atns.common.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

public class OrderBySqlFormula extends Order {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -2270985219658250705L;
    private final String sqlFormula;

// -------------------------- STATIC METHODS --------------------------

    public static Order sql(final String sqlFormula) {
        return new OrderBySqlFormula(sqlFormula);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    protected OrderBySqlFormula(final String sqlFormula) {
        super(sqlFormula, true);
        this.sqlFormula = sqlFormula;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public String toString() {
        return sqlFormula;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public String toSqlString(final Criteria criteria, final CriteriaQuery criteriaQuery) throws HibernateException {
        return sqlFormula;
    }
}
