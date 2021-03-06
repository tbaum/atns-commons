package de.atns.common.gwt.server;

import ch.lambdaj.function.convert.Converter;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author tbaum
 * @since 12.02.2010
 */
public abstract class ConvertingActionHandler<A extends Action<R>, R extends Result, S>
        extends DefaultActionHandler<A, R> implements ActionHandler<A, R> {

    private final Converter<S, R> converter;

    protected ConvertingActionHandler(final Converter<S, R> converter) {
        this.converter = converter;
    }

    @Override public final R executeInternal(final A action) throws ActionException {
        return converter.convert(executeInternal2(action));
    }

    public abstract S executeInternal2(final A action) throws ActionException;
}
