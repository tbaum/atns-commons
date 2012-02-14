package de.atns.common.security.benutzer;

import ch.lambdaj.function.convert.Converter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.Secured;
import de.atns.common.security.SecurityService;
import de.atns.common.security.benutzer.client.action.BenutzerChangePassword;
import de.atns.common.security.client.model.UserAdminRole;
import de.atns.common.security.client.model.UserPresentation;
import de.atns.common.security.model.Benutzer;
import de.atns.common.util.SHA1;

import javax.persistence.EntityManager;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerChangePasswordHandler extends DefaultActionHandler<BenutzerChangePassword, UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private final Converter<Benutzer, UserPresentation> converter;
    private final Provider<EntityManager> em;
    private final SecurityService securityService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerChangePasswordHandler(Provider<EntityManager> em, SecurityService securityService, UserConverter converter) {
        super(BenutzerChangePassword.class);
        this.em = em;
        this.securityService = securityService;
        this.converter = converter;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    @Transactional
    @Secured
    public UserPresentation executeInternal(final BenutzerChangePassword action) {
        final Benutzer t = (Benutzer) securityService.currentUser();

        final Benutzer benutzer = em.get().find(Benutzer.class, t.getId());

        if (!t.inRole(UserAdminRole.class) || t.getId() != benutzer.getId()) {
            throw new SecurityException("unable to change password.");
        }

        benutzer.setPasswort(SHA1.createSHA1Code(action.getPass()));
        return converter.convert(benutzer);
    }
}
