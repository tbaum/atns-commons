package de.atns.common.dao;

import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

/**
 * @author Thomas Baum
 * @since 10.09.2008
 */
public class AtnsNamingStrategy extends DefaultNamingStrategy implements NamingStrategy {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 1620435321715464036L;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface NamingStrategy ---------------------

    @Override
    public String classToTableName(final String s) {
        return tableName(super.classToTableName(removeImpl(s)));
    }

    @Override
    public String propertyToColumnName(final String s) {
        return super.propertyToColumnName(s);
    }

    @Override
    public String tableName(final String s) {
        return super.tableName(s);
    }

    @Override
    public String columnName(final String s) {
        return super.columnName(s);
    }

    @Override
    public String collectionTableName(final String s, final String s1, final String s2, final String s3,
                                      final String s4) {
        return super.collectionTableName(s, s1, s2, s3, s4);
    }

    @Override
    public String joinKeyColumnName(final String s, final String s1) {
        return super.joinKeyColumnName(s, s1);
    }

    @Override
    public String foreignKeyColumnName(final String s, final String s1, final String s2, final String s3) {
        return super.foreignKeyColumnName(s, s1, s2, s3);
    }

    @Override
    public String logicalColumnName(final String s, final String s1) {
        return super.logicalColumnName(s, s1);
    }

    @Override
    public String logicalCollectionTableName(final String s, final String s1, final String s2, final String s3) {
        return super.logicalCollectionTableName(s, s1, s2, s3);
    }

    @Override
    public String logicalCollectionColumnName(final String s, final String s1, final String s2) {
        return super.logicalCollectionColumnName(s, s1, s2);
    }

// -------------------------- OTHER METHODS --------------------------

    private String removeImpl(final String s) {
        return s.endsWith("Impl") ? s.substring(0, s.length() - 4) : s;
    }
}
