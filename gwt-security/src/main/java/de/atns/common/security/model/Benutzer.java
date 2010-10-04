package de.atns.common.security.model;

import de.atns.common.dao.BaseObject;
import de.atns.common.security.Secured;
import de.atns.common.security.SecurityUser;
import de.atns.common.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;

import static de.atns.common.security.model.DefaultRoles.ADMIN;
import static java.util.Arrays.asList;

@Entity
public class Benutzer extends BaseObject implements SecurityUser {
// ------------------------------ FIELDS ------------------------------

    @Column(unique = true, nullable = false)
    private String login;

    private String passwort;

    private String rollen;

    private String email;

// --------------------------- CONSTRUCTORS ---------------------------

    protected Benutzer() {
    }

    public Benutzer(final String login, final String passwort, final String email) {
        this.login = login;
        this.passwort = passwort;
        this.email = email;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override public String getLogin() {
        return login;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(final String passwort) {
        this.passwort = passwort;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SecurityUser ---------------------

    @Override public boolean hasAccessTo(final Secured secured) {
        String[] required = secured.value();
        if (required.length == 0) {
            return true;
        }

        for (String rolle : getRolle()) {
            if (contains(required, rolle)) {
                return true;
            }
        }

        return false;
    }

// -------------------------- OTHER METHODS --------------------------

    public void addRolle(final String... rollen) {
        ArrayList<String> rolle = new ArrayList<String>(asList(getRolle()));
        rolle.addAll(asList(rollen));
        this.rollen = StringUtils.join(",", rolle);
    }

    public String[] getRolle() {
        return rollen != null ? rollen.split(",") : new String[0];
    }

    public boolean isAdmin() {
        return contains(getRolle(), ADMIN);
    }

    private boolean contains(final String[] required, final String rolle) {
        for (String s : required) {
            if (s.equals(rolle)) {
                return true;
            }
        }
        return false;
    }

    public void removeRolle(final String... rollen) {
        ArrayList<String> rolle = new ArrayList<String>(asList(getRolle()));
        rolle.removeAll(asList(rollen));
        this.rollen = StringUtils.join(",", rolle);
    }
}
