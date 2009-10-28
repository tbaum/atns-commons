package de.atns.shop.tray;

import com.google.inject.BindingAnnotation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * @author tbaum
 * @since 27.09.2009 18:03:40
 */
@BindingAnnotation
@Retention(RUNTIME)
@Target({FIELD, PARAMETER, METHOD})
public @interface ServerPort {
}
