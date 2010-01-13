package de.atns.common.numbergenerator;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@BindingAnnotation
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface NumberDir {
}