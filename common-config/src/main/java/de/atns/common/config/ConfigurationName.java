package de.atns.common.config;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME) @Target(ANNOTATION_TYPE) public @interface ConfigurationName {
// -------------------------- OTHER METHODS --------------------------

    String value();
}
