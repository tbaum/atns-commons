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
public class HqlBuilder {
// ------------------------------ FIELDS ------------------------------

    private final StringBuilder filter = new StringBuilder();
    private final Map<String, Object> params = new HashMap<String, Object>();

// -------------------------- OTHER METHODS --------------------------

    public void and(String crit, Object... value) {
        addOp(crit, "AND", value);
    }

    private void addOp(String crit, String op, Object[] value) {
        final List<Object> paramNames = new ArrayList<Object>(value.length);
        for (Object v : value) {
            final String pname = "p" + params.size();
            params.put(pname, value);
            paramNames.add(pname);
        }

        filter.append(filter.length() == 0 ? " ( " : " " + op + " ( ");
        filter.append(MessageFormat.format(crit, paramNames.toArray()));
        filter.append(" )");
    }

    public String getWhere() {
        return filter.length() == 0 ? "" : " WHERE " + filter.toString();
    }

    public void or(String crit, Object... value) {
        addOp(crit, "OR", value);
    }

    public void setParameter(Query q) {
        for (Map.Entry<String, Object> e : params.entrySet()) {
            q.setParameter(e.getKey(), e.getValue());
        }
    }
}
