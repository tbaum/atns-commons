package de.atns.common.filter;

import java.lang.reflect.Field;

/**
 * @author tbaum
 * @since 09.07.11
 */
public class DeclaringClassFieldFilter implements Filter<Field> {
// ------------------------------ FIELDS ------------------------------

    private final Class<?> aClass;

// --------------------------- CONSTRUCTORS ---------------------------

    public DeclaringClassFieldFilter(Class<?> aClass) {
        this.aClass = aClass;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Filter ---------------------

    @Override public boolean isInFilter(Field o) {
        return o.getDeclaringClass().equals(aClass);
    }
}
