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
    private String name;
    private boolean admin;

// --------------------------- CONSTRUCTORS ---------------------------

    public BenutzerUpdate() {
    }

    public BenutzerUpdate(final long id, final boolean admin, final String login, final String passwort,
                          final String email, final String name) {
        this.id = id;
        this.admin = admin;
        this.login = login;
        this.email = email;
        this.passwort = passwort;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswort() {
        return passwort;
    }

    public boolean isAdmin() {
        return admin;
    }
}
