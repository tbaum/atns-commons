package de.atns.common.filter;

import java.lang.reflect.Field;

/**
 * @author tbaum
 * @since 09.07.11
 */
public class DeclaringClassFieldFilter implements Filter<Field> {

    private final Class<?> aClass;

    public DeclaringClassFieldFilter(Class<?> aClass) {
        this.aClass = aClass;
    }

    @Override public boolean isInFilter(Field o) {
        return Iterable.class.isAssignableFrom(o.getType()) || o.getDeclaringClass().equals(aClass);
    }
}
