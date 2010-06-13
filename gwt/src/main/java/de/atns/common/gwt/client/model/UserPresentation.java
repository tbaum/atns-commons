package de.atns.common.gwt.client.model;

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

// --------------------------- CONSTRUCTORS ---------------------------

    public UserPresentation() {
        this(null, null);
    }

    public UserPresentation(final String login, final String authToken) {
        this.login = login;
        this.authToken = authToken;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAuthToken() {
        return authToken;
    }

    public String getLogin() {
        return login;
    }

// -------------------------- OTHER METHODS --------------------------

    public boolean isValid() {
        return authToken != null;
    }
}
