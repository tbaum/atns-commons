package de.atns.common.dispatch.client;

public final class ServiceException extends Exception {
// --------------------------- CONSTRUCTORS ---------------------------

    public ServiceException() {
    }

    public ServiceException(final String message) {
        super(message);
    }
}
