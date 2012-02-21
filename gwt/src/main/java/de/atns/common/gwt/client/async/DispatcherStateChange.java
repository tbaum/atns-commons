package de.atns.common.gwt.client.async;

import com.google.gwt.event.shared.GwtEvent;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import java.util.Map;

/**
 * @author tbaum
 * @since 19.11.11
 */
public class DispatcherStateChange extends GwtEvent<DispatcherStateChangeHandler> {
// ------------------------------ FIELDS ------------------------------

    public static Type<DispatcherStateChangeHandler> TYPE = new Type<DispatcherStateChangeHandler>();
    private final Map<Class<? extends Action>, QueingCallback<? extends Result>> state;

// --------------------------- CONSTRUCTORS ---------------------------

    public DispatcherStateChange(Map<Class<? extends Action>, QueingCallback<? extends Result>> state) {
        this.state = state;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Map<Class<? extends Action>, QueingCallback<? extends Result>> getState() {
        return state;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void dispatch(DispatcherStateChangeHandler handler) {
        handler.onDispatcherStateChange(this);
    }

    @Override public Type<DispatcherStateChangeHandler> getAssociatedType() {
        return TYPE;
    }
}
