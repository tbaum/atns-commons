package de.atns.common.security.benutzer;

import ch.lambdaj.function.convert.Converter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import de.atns.common.gwt.server.DefaultActionHandler;
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
public class BenutzerUpdateHandler extends DefaultActionHandler<BenutzerUpdate, UserPresentation> {

    private final Provider<EntityManager> em;
    private final Converter<Benutzer, UserPresentation> converter;

    @Inject public BenutzerUpdateHandler(final Provider<EntityManager> em, final UserConverter converter) {
        this.em = em;
        this.converter = converter;
    }

    @Override @Transactional @Secured(UserAdminRole.class)
    public UserPresentation executeInternal(final BenutzerUpdate action) {
        UserPresentation p = action.getPresentation();

        final Benutzer benutzer = em.get().find(Benutzer.class, p.getId());

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

        return converter.convert(benutzer);
    }
}
