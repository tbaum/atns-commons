package de.atns.common.gwt.server;

import ch.lambdaj.function.convert.Converter;
import com.google.inject.persist.Transactional;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author tbaum
 * @since 12.02.2010
 */
public abstract class ConvertingActionHandler<A extends Action<R>, R extends Result, S> implements ActionHandler<A, R> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(ConvertingActionHandler.class);
    protected final Converter<S, R> converter;
    private final Class<A> clazz;

// --------------------------- CONSTRUCTORS ---------------------------

    protected ConvertingActionHandler(final Converter<S, R> converter, final Class<A> clazz) {
        this.converter = converter;
        this.clazz = clazz;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public final Class<A> getActionType() {
        return clazz;
    }

    @Override @Transactional
    public R execute(final A action, final ExecutionContext context) throws ActionException {
        final S result = executeInternal(action);
//        if (LOG.isDebugEnabled()) {
//            LOG.debug(result);
//        }
        return converter.convert(result);
    }

    @Override
    public final void rollback(final A action, final R result, final ExecutionContext context) {
    }

// -------------------------- OTHER METHODS --------------------------

    public abstract S executeInternal(final A action) throws ActionException;
}
