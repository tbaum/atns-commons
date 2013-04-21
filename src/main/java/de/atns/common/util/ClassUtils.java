package de.atns.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tbaum
 * @since 11.05.2010
 */
public class ClassUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ClassUtils.class);

    public static <T> T construct(final Class<? extends T> judgeClass, final Object... args) {
        final Class[] argsC = getClasses(args);

        try {
            final Constructor<? extends T> con = judgeClass.getConstructor(argsC);
            return con.newInstance(args);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static Class[] getClasses(final Object... args) {
        final Class[] argsC = new Class[args.length];

        for (int i = 0; i < args.length; i++) {
            argsC[i] = args[i].getClass();
        }
        return argsC;
    }

    public static <T> T construct(final Class<? extends T> judgeClass, final Class[] argsC, final Object... args) {
        try {
            final Constructor<? extends T> con = judgeClass.getConstructor(argsC);
            return con.newInstance(args);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static void setField(final Object target, final Field field, final Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error setting field " + field, e);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T getField(final Object target, final Field field) {
        try {
            field.setAccessible(true);
            return (T) field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error getting field " + field, e);
        } catch (ClassCastException e) {
            throw new RuntimeException("Error getting field " + field, e);
        }
    }

    public static <BEAN> BEAN newInstance(final Class<BEAN> aClass) {
        try {
            return aClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class[] createInterfaceList(final Class superClass, final Class... classes) {
        final List<Class> interfaces = new ArrayList<Class>();
        if (superClass.isInterface()) {
            interfaces.add(superClass);
        } else {
            Collections.addAll(interfaces, superClass.getInterfaces());
        }
        Collections.addAll(interfaces, classes);
        return interfaces.toArray(new Class[interfaces.size()]);
    }

    public static RuntimeException convertToRuntimeException(final Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        if (e instanceof InvocationTargetException) {
            return convertToRuntimeException(((InvocationTargetException) e).getTargetException());
        }
        //noinspection ThrowableInstanceNeverThrown
        return new RuntimeException(e);
    }
}
