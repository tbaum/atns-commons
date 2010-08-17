package de.atns.common.security.benutzer.client.action;

import de.atns.common.gwt.client.model.EmptyResult;
import net.customware.gwt.dispatch.shared.Action;


/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerUpdate implements Action<EmptyResult> {
// ------------------------------ FIELDS ------------------------------

    private String login;
    private String email;
    private String passwort;
    private boolean admin;

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerUpdate() {
    }

    public BenutzerUpdate(final boolean admin, final String login, final String email, final String passwort) {
        this.admin = admin;
        this.login = login;
        this.email = email;
        this.passwort = passwort;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswort() {
        return passwort;
    }

    public boolean isAdmin() {
        return admin;
    }
}