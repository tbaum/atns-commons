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
 *        final ActionForwardInterceptor forwardInterceptor = new ActionForwardInterceptor();
 *        requestInjection(forwardInterceptor);
 *        bindInterceptor(subclassesOf(Action.class).and(annotatedWith(ForwardTo.class)),
 *        any(), forwardInterceptor);
 */
public class ActionForwardInterceptor implements MethodInterceptor {
// ------------------------------ FIELDS ------------------------------

    private Provider<Forwarder> forwarder;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Inject public void setForwarder(final Provider<Forwarder> forwarder) {
        this.forwarder = forwarder;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface MethodInterceptor ---------------------

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final Object result = invocation.proceed();

        final Method method = invocation.getMethod();
        if ("service".equals(method.getName())) {
            final ForwardTo forward = method.getDeclaringClass().getAnnotation(ForwardTo.class);
            forwarder.get().forward(forward.value());
        }

        return result;
    }
}
