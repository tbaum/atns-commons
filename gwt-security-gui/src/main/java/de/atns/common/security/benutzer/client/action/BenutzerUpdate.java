package de.atns.common.security.benutzer.client.action;

import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import net.customware.gwt.dispatch.shared.Action;


/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerUpdate implements Action<BenutzerPresentation> {
// ------------------------------ FIELDS ------------------------------

    private Long id;
    private String login;
    private String email;
    private String passwort;
    private boolean admin;

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerUpdate() {
    }

    public BenutzerUpdate(final long id, final boolean admin, final String login, final String passwort,
                          final String email) {
        this.id = id;
        this.admin = admin;
        this.login = login;
        this.email = email;
        this.passwort = passwort;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
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