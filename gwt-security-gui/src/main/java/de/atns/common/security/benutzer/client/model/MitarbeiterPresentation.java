package de.atns.common.security.benutzer.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.dispatch.shared.Result;


/**
 * @author tbaum
 * @since 11.02.2010
 */
public class MitarbeiterPresentation implements Result, IsSerializable {
// ------------------------------ FIELDS ------------------------------

    private String login;
    private boolean admin;

// --------------------------- CONSTRUCTORS ---------------------------

    protected MitarbeiterPresentation() {
    }

    public MitarbeiterPresentation(final String login, final boolean admin) {
        this.login = login;
        this.admin = admin;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getLogin() {
        return login;
    }

    public boolean isAdmin() {
        return admin;
    }
}