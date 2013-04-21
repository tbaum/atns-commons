package de.atns.common.security.benutzer;

import ch.lambdaj.function.convert.Converter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.extensions.security.Secured;
import com.google.inject.extensions.security.SecurityService;
import com.google.inject.persist.Transactional;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.benutzer.client.action.BenutzerChangePassword;
import de.atns.common.security.client.model.UserAdminRole;
import de.atns.common.security.client.model.UserPresentation;
import de.atns.common.security.model.Benutzer;

import javax.persistence.EntityManager;

import static de.atns.common.security.benutzer.UserServiceImpl.createSHA1Code;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerChangePasswordHandler extends DefaultActionHandler<BenutzerChangePassword, UserPresentation> {

    private final Converter<Benutzer, UserPresentation> converter;
    private final Provider<EntityManager> em;
    private final SecurityService securityService;

    @Inject public BenutzerChangePasswordHandler(final Provider<EntityManager> em,
                                                 final SecurityService securityService,
                                                 final UserConverter converter) {
        this.em = em;
        this.securityService = securityService;
        this.converter = converter;
    }

    @Override @Transactional @Secured public UserPresentation executeInternal(final BenutzerChangePassword action) {
        final Benutzer t = (Benutzer) securityService.currentUser();

        final Benutzer benutzer = em.get().find(Benutzer.class, t.getId());

        if (!t.inRole(UserAdminRole.class) || t.getId() != benutzer.getId()) {
            throw new SecurityException("unable to change password.");
        }

        benutzer.setPasswort(createSHA1Code(action.getPass()));
        return converter.convert(benutzer);
    }
}
