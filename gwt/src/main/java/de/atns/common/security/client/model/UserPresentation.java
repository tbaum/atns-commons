package de.atns.common.security.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.security.SecurityRole;
import de.atns.common.security.SecurityRolePresentation;
import net.customware.gwt.dispatch.shared.Result;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author tbaum
 * @since 11.02.2010
 */
public class UserPresentation implements Result, IsSerializable {
// ------------------------------ FIELDS ------------------------------

    private Long id;
    private String login;
    private String authToken;
    private TreeSet<SecurityRolePresentation> roles = new TreeSet<SecurityRolePresentation>();
    private String passwort;
    private String email;
    private String name;

// -------------------------- STATIC METHODS --------------------------

    public static UserPresentation invalidUser() {
        return new UserPresentation();
    }

    public static UserPresentation nameToken(String token, String login,
                                             Set<SecurityRolePresentation> roles) {
        final UserPresentation userPresentation = new UserPresentation(null, login, null, null, null, roles);
        userPresentation.setAuthToken(token);
        return userPresentation;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public UserPresentation() {
    }

    public UserPresentation(Long id, String login, String name, String passwort, String email,
                            Set<SecurityRolePresentation> roles) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.passwort = passwort;
        this.email = email;
        this.roles = new TreeSet<SecurityRolePresentation>(roles);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAuthToken() {
        return authToken;
    }

    protected void setAuthToken(String token) {
        this.authToken = token;
    }

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

    public String getPasswort() {
        return passwort;
    }

    public Set<SecurityRolePresentation> getRoles() {
        return roles;
    }

// -------------------------- OTHER METHODS --------------------------

    public boolean inRole(Class<? extends SecurityRole>... required) {
        if (required.length == 0) {
            return true;
        }

        if (roles == null) return false;

        for (final SecurityRolePresentation myRole : roles) {
            for (final Class<? extends SecurityRole> s : required) {
                if (s.equals(myRole.getRole())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValid() {
        return authToken != null;
    }
}
