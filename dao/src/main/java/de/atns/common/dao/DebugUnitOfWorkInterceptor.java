package de.atns.common.dao;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author tbaum
 * @since 25.05.11 18:13
 */
public class DebugUnitOfWorkInterceptor implements MethodInterceptor {

    private static final Log LOG = LogFactory.getLog(DebugUnitOfWorkInterceptor.class);
    int open = 0;
    int deep = 0;
    ThreadLocal<Timer> t = new ThreadLocal<Timer>();

    @Override public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
        deep++;
        final String methodName = methodInvocation.getMethod().getName();

        if ("end".equals(methodName)) {
            open--;
            final Timer timer = t.get();
            if (timer != null) {
                timer.cancel();
                t.remove();
            }
        }
        if ("begin".equals(methodName)) {
            open++;
            final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            final List<String> rs = dumpTrace(stackTrace, createInfoName(methodName));

            final Timer tt = new Timer(false);
            t.set(tt);

            tt.schedule(new TimerTask() {
                @Override public void run() {
                    LOG.debug("long transaction, or unclosed unit-of-work found !!!");
                    for (String r : rs) {
                        LOG.debug(r);
                    }
                }
            }, 30000);
        }
        try {
            return methodInvocation.proceed();
        } finally {
            deep--;
        }
    }

    private String createInfoName(final String methodName) {
        return methodName + " d:" + deep + " open:" + open + " " + Thread.currentThread().getName();
    }

    private List<String> dumpTrace(final StackTraceElement[] stackTrace, final String msg) {
        List<String> result = new ArrayList<String>();
        for (int i = 1; i < stackTrace.length; i++) {
            final StackTraceElement stackTraceElement = stackTrace[i];
            // if (stackTraceElement.getClassName().startsWith("com.google.inject.internal"))
            //    continue;

            if (stackTraceElement.getClassName().startsWith("de.atns")) {
                result.add(String.format("%s %2d:%s", msg, i, stackTraceElement));
            }
        }
        return result;
    }
}
