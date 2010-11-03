package de.atns.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.PersistenceException;

/**
 * @author tbaum
 * @since 25.10.2009
 */
public class ExceptionInterceptor implements MethodInterceptor {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(ExceptionInterceptor.class);

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface MethodInterceptor ---------------------

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (PersistenceException e) {
            LOG.error(e, e);
            throw new RuntimeException(e.getMessage());
        } catch (SecurityException e) {
            LOG.error(e, e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
