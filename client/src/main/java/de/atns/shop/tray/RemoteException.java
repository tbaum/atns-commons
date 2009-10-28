package de.atns.shop.tray;

import java.io.IOException;

/**
 * @author tbaum
 * @since 26.09.2009 17:51:02
 */
public class RemoteException extends RuntimeException {
// ------------------------------ FIELDS ------------------------------

    private final String response;

// -------------------------- STATIC METHODS --------------------------

    public static RemoteException createError(final String response) throws IOException {
        if (response.contains("AuthenticationCredentialsNotFoundException") ||
                response.contains("BadCredentialsException")) {
            return new RemoteAuthenticationException(response);
        }
        return new RemoteException(response);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    protected RemoteException(final String reponse) {
        this.response = reponse;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getResponse() {
        return response;
    }
}
