package net.customware.gwt.dispatch.shared;

/**
 * These are thrown by {@link net.customware.gwt.dispatch.server.Dispatch#execute(net.customware.gwt.dispatch.shared.Action)} if there is a
 * problem executing a particular {@link net.customware.gwt.dispatch.shared.Action}.
 *
 * @author David Peterson
 */
public class ActionException extends DispatchException {

    protected ActionException() {
    }

    public ActionException(String message) {
        super(message);
    }

    public ActionException(Throwable cause) {
        super(cause);
    }

    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }

}
