package de.atns.common.security.benutzer.client.action;

import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import net.customware.gwt.dispatch.shared.Action;


/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerCreate implements Action<BenutzerPresentation> {
// ------------------------------ FIELDS ------------------------------

    private String login;
    private String email;
    private String passwort;
    private String name;
    private boolean admin;
    private String rollen;

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerCreate() {
    }

    public BenutzerCreate(final boolean admin, final String login, final String passwort, final String email,
                          final String name, final String rollen) {
        this.admin = admin;
        this.login = login;
        this.passwort = passwort;
        this.email = email;
        this.name = name;
        this.rollen = rollen;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPasswort() {
        return passwort;
    }

    public String getRollen() {
        return rollen;
    }

    public boolean isAdmin() {
        return admin;
    }
}
