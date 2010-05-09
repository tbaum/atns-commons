package org.json;

/**
 * The JSONException is thrown by the JSON.org classes then things are amiss.
 *
 * @author JSON.org
 * @version 2008-09-18
 */
public class JSONException extends Exception {
// ------------------------------ FIELDS ------------------------------

    private Throwable cause;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Constructs a JSONException with an explanatory message.
     *
     * @param message Detail about the reason for the exception.
     */
    public JSONException(final String message) {
        super(message);
    }

    public JSONException(final Throwable t) {
        super(t.getMessage());
        this.cause = t;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override public Throwable getCause() {
        return this.cause;
    }
}
