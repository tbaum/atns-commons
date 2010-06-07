package de.atns.common.gwt.server;

import ch.lambdaj.function.convert.Converter;
import com.wideplay.warp.persist.Transactional;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author tbaum
 * @since 12.02.2010
 */
public abstract class ConvertingActionHandler<A extends Action<R>, R extends Result, S>
        implements ActionHandler<A, R> {
// ------------------------------ FIELDS ------------------------------

    protected final Converter<S, R> converter;

// --------------------------- CONSTRUCTORS ---------------------------

    protected ConvertingActionHandler(final Converter<S, R> converter) {
        this.converter = converter;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override @Transactional
    public final R execute(final A action, ExecutionContext context) throws ActionException {
        return converter.convert(executeInternal(action));
    }

    public abstract S executeInternal(final A action) throws ActionException;
}
