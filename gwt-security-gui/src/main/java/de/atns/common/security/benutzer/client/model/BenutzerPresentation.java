package de.atns.common.security.benutzer.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.dispatch.shared.Result;


/**
 * @author tbaum
 * @since 11.02.2010
 */
public class BenutzerPresentation implements Result, IsSerializable {
// ------------------------------ FIELDS ------------------------------

    private String login;
    private boolean admin;
    private String email;

// --------------------------- CONSTRUCTORS ---------------------------

    protected BenutzerPresentation() {
    }

    public BenutzerPresentation(final String login, final boolean admin, final String email) {
        this.login = login;
        this.admin = admin;
        this.email = email;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public boolean isAdmin() {
        return admin;
    }
}