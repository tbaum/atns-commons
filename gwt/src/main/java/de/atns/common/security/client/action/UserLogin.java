package de.atns.common.security.client.action;

import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.shared.Action;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class UserLogin implements Action<UserPresentation> {

    private String userName;
    private String password;

    protected UserLogin() {
    }

    public UserLogin(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
