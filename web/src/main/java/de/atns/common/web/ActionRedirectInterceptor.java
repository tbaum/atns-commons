package de.atns.common.web;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author tbaum
 * @since 25.10.2009
 *        <p/>
 *        final ActionRedirectInterceptor redirectInterceptor = new ActionRedirectInterceptor();
 *        requestInjection(redirectInterceptor);
 *        bindInterceptor(subclassesOf(Action.class).and(annotatedWith(RedirectTo.class)),
 *        any(), redirectInterceptor);
 */
public class ActionRedirectInterceptor implements MethodInterceptor {
// ------------------------------ FIELDS ------------------------------

    private Provider<Forwarder> forwarder;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Inject public void setForwarder(final Provider<Forwarder> forwarder) {
        this.forwarder = forwarder;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface MethodInterceptor ---------------------

    @Override public Object invoke(final MethodInvocation invocation) throws Throwable {
        final Object result = invocation.proceed();

        final Method method = invocation.getMethod();
        if ("service".equals(method.getName())) {
            final RedirectTo redirect = method.getDeclaringClass().getAnnotation(RedirectTo.class);
            forwarder.get().redirect(redirect.value());
        }

        return result;
    }
}
