package de.atns.common.exception;

import org.apache.commons.logging.Log;

public class BaseException extends RuntimeException {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 3290922922289763229L;
    private boolean logged;

// --------------------------- CONSTRUCTORS ---------------------------

    public BaseException() {
    }

    public BaseException(final String s) {
        super(s);
    }

    public BaseException(final Throwable t) {
        super(t);
    }

    public BaseException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

// -------------------------- OTHER METHODS --------------------------

    public void log(final Log log, final String message) {
        if (!logged) {
            if (log.isErrorEnabled()) {
                log.error(message);
                logged = true;
            }
        }
    }
}
