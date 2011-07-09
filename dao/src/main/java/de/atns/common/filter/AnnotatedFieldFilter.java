package de.atns.common.filter;

import de.atns.common.filter.Filter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author tbaum
 * @since 09.07.11
 */
public class AnnotatedFieldFilter implements Filter<Field> {
// ------------------------------ FIELDS ------------------------------

    private final Class<? extends Annotation> annotation;

// --------------------------- CONSTRUCTORS ---------------------------

    public AnnotatedFieldFilter(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Filter ---------------------

    @Override public boolean isInFilter(Field o) {
        return o.getAnnotation(annotation) == null;
    }
}
