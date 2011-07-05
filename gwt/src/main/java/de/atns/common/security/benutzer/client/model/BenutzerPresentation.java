package de.atns.common.security.benutzer.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.dispatch.shared.Result;

import java.io.Serializable;

/**
 * @author tbaum
 * @since 11.02.2010
 */
public class BenutzerPresentation implements Result, Serializable, IsSerializable {
// ------------------------------ FIELDS ------------------------------

    private String login;
    private boolean admin;
    private String email;
    private String name;
    private long id;

// --------------------------- CONSTRUCTORS ---------------------------

    protected BenutzerPresentation() {
    }

    public BenutzerPresentation(final long id, final String login, final boolean admin, final String email,
                                final String name) {
        this.id = id;
        this.login = login;
        this.admin = admin;
        this.email = email;
        this.name = name;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return admin;
    }
}
