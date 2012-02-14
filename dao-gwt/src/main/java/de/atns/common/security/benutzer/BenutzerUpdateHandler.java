package de.atns.common.security.benutzer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import de.atns.common.gwt.server.ConvertingActionHandler;
import de.atns.common.security.Secured;
import de.atns.common.security.SecurityRole;
import de.atns.common.security.SecurityRolePresentation;
import de.atns.common.security.benutzer.client.action.BenutzerUpdate;
import de.atns.common.security.client.model.UserAdminRole;
import de.atns.common.security.client.model.UserPresentation;
import de.atns.common.security.model.Benutzer;
import de.atns.common.util.SHA1;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerUpdateHandler extends ConvertingActionHandler<BenutzerUpdate, UserPresentation, Benutzer> {
// ------------------------------ FIELDS ------------------------------

    private final Provider<EntityManager> em;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public BenutzerUpdateHandler(final Provider<EntityManager> em) {
        super(UserConverter.USER_CONVERTER, BenutzerUpdate.class);
        this.em = em;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override @Transactional @Secured(UserAdminRole.class)
    public Benutzer executeInternal(final BenutzerUpdate action) {
        final EntityManager em = this.em.get();

        UserPresentation p = action.getPresentation();

        final Benutzer benutzer = em.find(Benutzer.class, p.getId());

        if (!p.getPasswort().isEmpty()) {
            benutzer.setPasswort(SHA1.createSHA1Code(p.getPasswort()));
        }

        benutzer.setEmail(p.getEmail());
        benutzer.setName(p.getName());

        Set<Class<? extends SecurityRole>> roles = new HashSet<Class<? extends SecurityRole>>();
        for (SecurityRolePresentation presentation : p.getRoles()) {
            roles.add(presentation.getRole());
        }
        benutzer.setRole(roles);

        return benutzer;
    }
}
