package de.atns.common.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

public class OrderBySqlFormula extends Order {

    private final String sqlFormula;

    public static Order sql(final String sqlFormula) {
        return new OrderBySqlFormula(sqlFormula);
    }

    protected OrderBySqlFormula(final String sqlFormula) {
        super(sqlFormula, true);
        this.sqlFormula = sqlFormula;
    }

    @Override public String toString() {
        return sqlFormula;
    }

    @Override
    public String toSqlString(final Criteria criteria, final CriteriaQuery criteriaQuery) throws HibernateException {
        return sqlFormula;
    }
}
