package de.atns.common.util;

import java.lang.reflect.Constructor;

import static de.atns.common.util.ClassUtils.convertToRuntimeException;
import static java.util.Arrays.asList;

/**
 * @author Thomas Baum
 * @since 12.09.2008
 */
public class ConstructorRef<TYPE> {

    private final Constructor<TYPE> constructor;

    private ConstructorRef(final Constructor<TYPE> constructor) {
        if (constructor == null) {
            throw new IllegalArgumentException("no constructor given");
        }
        this.constructor = constructor;
    }

    public static <TYPE> ConstructorRef<TYPE> lookupContructor(final Class<TYPE> clazz, final Class... args) {
        try {
            final Constructor<TYPE> constructor = clazz.getConstructor(args);
            return new ConstructorRef<TYPE>(constructor);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("unable to lookup constructor for args " + asList(args), e);
        }
    }

    public TYPE createInstance(final Object... args) throws RuntimeException {
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw convertToRuntimeException(e);
        }
    }
}
