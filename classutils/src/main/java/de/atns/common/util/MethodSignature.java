package de.atns.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.reflect.Proxy.getProxyClass;

/**
 * @author tbaum
 * @since 20.02.12
 */

public class MethodSignature<TARGET> {

    private final TARGET instance;
    private final ThreadLocal<Method> called = new ThreadLocal<Method>();

    public static <T> MethodSignature<T> signature(Class<T> target) {
        return new MethodSignature<T>(target);
    }

    private MethodSignature(final Class<TARGET> target) {
        try {
            final ClassLoader classLoader = MethodSignature.class.getClassLoader();
            this.instance = target.cast(getProxyClass(classLoader, target).
                    getConstructor(InvocationHandler.class).
                    newInstance(new InvocationHandler()));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public TARGET call() {
        called.set(null);
        return instance;
    }

    public Method getCalled() {
        return called.get();
    }

    class InvocationHandler implements java.lang.reflect.InvocationHandler {
        @Override public Object invoke(Object proxy, Method method, Object[] args) {
            called.set(method);
            return null;
        }
    }
}
