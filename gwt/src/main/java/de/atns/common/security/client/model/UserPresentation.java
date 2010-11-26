package de.atns.common.security.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author tbaum
 * @since 11.02.2010
 */
public class UserPresentation implements Result, IsSerializable {
// ------------------------------ FIELDS ------------------------------

    private String login;
    private String authToken;
    private String[] roles;

// --------------------------- CONSTRUCTORS ---------------------------

    public UserPresentation() {
        this(null, null);
    }

    public UserPresentation(final String login, final String authToken, final String... roles) {
        this.login = login;
        this.authToken = authToken;
        this.roles = roles;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAuthToken() {
        return authToken;
    }

    public String getLogin() {
        return login;
    }

    public String[] getRoles() {
        return roles;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override public String toString() {
        return "UserPresentation{" +
                "login='" + login + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }

// -------------------------- OTHER METHODS --------------------------

    public boolean inRole(final String targetRole) {
        for (final String role : roles) {
            if (role.equals(targetRole)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValid() {
        return authToken != null;
    }
}
