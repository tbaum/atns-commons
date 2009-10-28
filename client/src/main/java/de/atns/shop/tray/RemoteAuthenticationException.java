package de.atns.shop.tray;

/**
 * @author tbaum
 * @since 26.09.2009 17:53:25
 */
public class RemoteAuthenticationException extends RemoteException {
// --------------------------- CONSTRUCTORS ---------------------------

    protected RemoteAuthenticationException(final String reponse) {
        super(reponse);
    }
}
