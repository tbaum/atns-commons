package de.atns.common.filter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author tbaum
 * @since 09.07.11
 */
public class AnnotatedFieldFilter implements Filter<Field> {

    private final Class<? extends Annotation> annotation;

    public AnnotatedFieldFilter(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    @Override public boolean isInFilter(Field o) {
        return o.getAnnotation(annotation) == null;
    }
}
