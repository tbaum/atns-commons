package de.atns.common.gwt.client.action;

import de.atns.common.gwt.client.model.UserPresentation;
import net.customware.gwt.dispatch.shared.Action;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class UserLogin implements Action<UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private String userName;
    private String password;

// --------------------------- CONSTRUCTORS ---------------------------

    protected UserLogin() {
    }

    public UserLogin(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
