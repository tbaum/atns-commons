package de.atns.common.security.model;

import com.google.inject.extensions.security.Secured;
import com.google.inject.extensions.security.SecurityRole;
import com.google.inject.extensions.security.SecurityUser;
import de.atns.common.dao.BaseObject;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.google.inject.extensions.security.ClassHelper.resolveAll;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.InheritanceType.JOINED;

@Entity @Inheritance(strategy = JOINED)
public class Benutzer extends BaseObject implements SecurityUser {

    @Column(unique = true, nullable = false) private String login;
    private String passwort;
    @ElementCollection(fetch = EAGER, targetClass = Class.class)
    private Set<Class<? extends SecurityRole>> roles = new HashSet<Class<? extends SecurityRole>>();
    private String email;
    @Transient private String token;
    private String name;
    private Date lastLogin;
    private Date lastAccess;

    protected Benutzer() {
    }

    public Benutzer(final String login, final String passwort, final String email, final String name) {
        this.login = login;
        this.passwort = passwort;
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    @Override public String getLogin() {
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

    public void setPasswort(final String passwort) {
        this.passwort = passwort;
    }

    @Override public Set<Class<? extends SecurityRole>> getRoles() {
        return roles;
    }

    @Override public String getToken() {
        return token;
    }

    @Override public void setToken(final String token) {
        this.token = token;
    }

    @Override public boolean hasAccessTo(final Secured secured) {
        return inRole(secured.value());
    }

    public void addRolle(final Class<? extends SecurityRole> rolle) {
        roles.add(rolle);
    }

    public void clearLastAccess() {
        this.lastAccess = null;
    }

    public boolean inRole(Class<? extends SecurityRole>... required) {
        if (required.length == 0) {
            return true;
        }

        for (final Class<? extends SecurityRole> myRole : roles) {
            for (final Class<? extends SecurityRole> s : required) {
                if (resolveAll(myRole).contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setLastAccess() {
        this.lastAccess = new Date();
    }

    public void setLastLogin() {
        this.lastLogin = new Date();
    }

    public void setRole(final Set<Class<? extends SecurityRole>> role) {
        this.roles = role;
    }
}
