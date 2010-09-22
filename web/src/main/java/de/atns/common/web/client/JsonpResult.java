package de.atns.common.web.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

/**
 * @author tbaum
 * @since 14.02.2010
 */
public class JsonpResult<R extends JavaScriptObject> extends JavaScriptObject {
// --------------------------- CONSTRUCTORS ---------------------------

    protected JsonpResult() {
    }

// -------------------------- OTHER METHODS --------------------------

    public final Error error() {
        return getStatus().equals("error") ? (Error) getResult() : null;
    }

    private native R getResult() /*-{
        return this.result;
    }-*/;

    private native String getStatus() /*-{
        return this.status;
    }-*/;

    public final R result() {
        return getStatus().equals("ok") ? getResult() : null;
    }

// -------------------------- INNER CLASSES --------------------------

    public static class Error extends JavaScriptObject {
        protected Error() {
        }

        public final native String getMessage() /*-{
            return this.message;
        }-*/;

        public final native JsArrayString getStacktrace() /*-{
            return this.stacktrace;
        }-*/;
    }
}
