package de.atns.common.dao;

import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tbaum
 * @since 02.11.10
 */
public class QueryBuilder {
// ------------------------------ FIELDS ------------------------------

    private final StringBuilder filter = new StringBuilder();
    private final Map<String, Object> params = new HashMap<String, Object>();

// -------------------------- OTHER METHODS --------------------------

    public void and(final String crit, final Object... values) {
        addOp(crit, "AND", values);
    }

    private void addOp(final String crit, final String op, final Object[] values) {
        final List<Object> paramNames = new ArrayList<Object>(values.length);
        for (final Object value : values) {
            final String pname = "p" + params.size();
            params.put(pname, value);
            paramNames.add(":" + pname);
        }

        filter.append(filter.length() == 0 ? " ( " : " " + op + " ( ");
        filter.append(MessageFormat.format(crit, paramNames.toArray()));
        filter.append(" )");
    }

    public String getWhere() {
        return filter.length() == 0 ? "" : " WHERE " + filter.toString();
    }

    public void or(final String crit, final Object... values) {
        addOp(crit, "OR", values);
    }

    public void setParameter(final Query q) {
        for (final Map.Entry<String, Object> e : params.entrySet()) {
            q.setParameter(e.getKey(), e.getValue());
        }
    }
}
