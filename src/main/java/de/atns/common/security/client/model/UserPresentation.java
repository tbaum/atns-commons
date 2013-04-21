package de.atns.common.security.client.model;

import com.google.inject.extensions.security.SecurityRole;
import net.customware.gwt.dispatch.shared.Result;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author tbaum
 * @since 11.02.2010
 */
public class UserPresentation implements Result, Serializable {

    private Long id;
    private String login;
    private String authToken;
    private TreeSet<SecurityRolePresentation> roles = new TreeSet<SecurityRolePresentation>();
    private String passwort;
    private String email;
    private String name;
    private Date lastLogin;
    private Date lastAccess;

    public UserPresentation() {
    }

    public UserPresentation(UserPresentation other) {
        this(other.id, other.login, other.name, other.passwort, other.email, other.roles, other.lastLogin,
                other.lastAccess);
    }

    public UserPresentation(Long id, String login, String name, String passwort, String email,
                            Set<SecurityRolePresentation> roles, Date lastLogin, Date lastAccess) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.passwort = passwort;
        this.email = email;
        this.lastLogin = lastLogin;
        this.lastAccess = lastAccess;
        this.roles = new TreeSet<SecurityRolePresentation>(roles);
    }

    public static UserPresentation invalidUser() {
        return new UserPresentation();
    }

    public static UserPresentation nameToken(String token, String login, Set<SecurityRolePresentation> roles) {
        final UserPresentation user = new UserPresentation(null, login, null, null, null, roles, null, null);
        user.setAuthToken(token);
        return user;
    }

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

    public Date getLastAccess() {
        return lastAccess;
    }

    public Date getLastLogin() {
        return lastLogin;
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

    public Set<String> getRoleNames() {
        Set<String> roleNames = new HashSet<String>();
        for (SecurityRolePresentation presentation : roles) {
            roleNames.add(presentation.getRoleName());
        }
        return roleNames;
    }

    public Set<SecurityRolePresentation> getRoles() {
        return roles;
    }

    public boolean inRole(Class<? extends SecurityRole> required) {
        //noinspection unchecked
        return inRole(new Class[]{required});
    }

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
