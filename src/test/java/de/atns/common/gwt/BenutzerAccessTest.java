package de.atns.common.gwt;

import com.google.inject.extensions.security.Secured;
import com.google.inject.extensions.security.SecurityRole;
import de.atns.common.security.client.model.UserAdminRole;
import de.atns.common.security.model.Benutzer;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author tbaum
 * @since 10.12.11
 */
public class BenutzerAccessTest {

    @Test
    public void hasAccessTo() throws Exception {
        Benutzer mitarbeiter = new Benutzer(null, null, null, null);

        mitarbeiter.addRolle(AdminRole.class);
        assertTrue(mitarbeiter.hasAccessTo(createSecured(AdminRole.class)));
        assertTrue(mitarbeiter.hasAccessTo(createSecured(UserAdminRole.class)));
        assertTrue(mitarbeiter.hasAccessTo(createSecured()));
    }

    private Secured createSecured(final Class<? extends SecurityRole>... roles) {
        return new Secured() {
            @Override public Class<? extends SecurityRole>[] value() {
                return roles;
            }

            @Override public Class<? extends Annotation> annotationType() {
                return null;
            }
        };
    }

    @Test
    public void hasAccessToNonRole() throws Exception {
        Benutzer mitarbeiter = new Benutzer(null, null, null, null);

        assertFalse(mitarbeiter.hasAccessTo(createSecured(AdminRole.class)));
        assertFalse(mitarbeiter.hasAccessTo(createSecured(TestRole.class)));
        assertTrue(mitarbeiter.hasAccessTo(createSecured()));
    }

    @Test
    public void hasAccessToUserRoles() throws Exception {
        Benutzer mitarbeiter = new Benutzer(null, null, null, null);
        mitarbeiter.addRolle(TestRole.class);
        mitarbeiter.addRolle(TestRole3.class);
        assertFalse(mitarbeiter.hasAccessTo(createSecured(AdminRole.class)));
        assertTrue(mitarbeiter.hasAccessTo(createSecured(TestRole.class)));
        assertFalse(mitarbeiter.hasAccessTo(createSecured(TestRole2.class)));
        assertTrue(mitarbeiter.hasAccessTo(createSecured(TestRole3.class)));
        assertTrue(mitarbeiter.hasAccessTo(createSecured()));
    }

    private interface AdminRole extends SecurityRole, UserAdminRole {
    }

    private interface TestRole extends SecurityRole {
    }

    private interface TestRole2 extends SecurityRole {
    }

    private interface TestRole3 extends SecurityRole {
    }
}
