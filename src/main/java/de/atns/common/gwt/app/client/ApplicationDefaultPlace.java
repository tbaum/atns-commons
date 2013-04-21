/*
 * Created by IntelliJ IDEA.
 * User: tbaum
 * Date: 18.06.2010
 * Time: 12:15:02
 */
package de.atns.common.gwt.app.client;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER})
@BindingAnnotation
public @interface ApplicationDefaultPlace {
}
