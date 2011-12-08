package de.atns.common.security.benutzer.client.action;

import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.shared.Action;


/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerUpdate implements Action<UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private String id;
    private String login;
    private String email;
    private String passwort;
    private String name;
    private boolean admin;
    private String rollen;

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerUpdate() {
    }

    public BenutzerUpdate(final String id, final boolean admin, final String login, final String passwort,
                          final String email, final String name, final String rollen) {
        this.id = id;
        this.admin = admin;
        this.login = login;
        this.email = email;
        this.passwort = passwort;
        this.name = name;
        this.rollen = rollen;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
