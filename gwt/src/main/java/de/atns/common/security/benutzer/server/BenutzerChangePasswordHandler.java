package de.atns.common.security.benutzer.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import de.atns.common.gwt.server.ConvertingActionHandler;
import de.atns.common.security.Secured;
import de.atns.common.security.SecurityService;
import de.atns.common.security.benutzer.client.action.BenutzerChangePassword;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import de.atns.common.security.model.Benutzer;
import de.atns.common.util.SHA1;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;

import static de.atns.common.security.benutzer.server.BenutzerPresentationConverter.BENUTZER_CONVERTER;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerChangePasswordHandler
        extends ConvertingActionHandler<BenutzerChangePassword, BenutzerPresentation, Benutzer> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(BenutzerChangePasswordHandler.class);
    private final Provider<EntityManager> em;
    private final SecurityService securityService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerChangePasswordHandler(final Provider<EntityManager> em, final SecurityService securityService) {
        super(BENUTZER_CONVERTER, BenutzerChangePassword.class);
        this.em = em;
        this.securityService = securityService;
    }

// -------------------------- OTHER METHODS --------------------------

    @Transactional @Secured @Override public Benutzer executeInternal(final BenutzerChangePassword action) {
        final Benutzer t = (Benutzer) securityService.currentUser();

        final Benutzer benutzer = em.get().find(Benutzer.class, t.getId());

        if (!t.isAdmin() || t.getId() != benutzer.getId()) {
            throw new SecurityException("unable to change password.");
        }

        benutzer.setPasswort(SHA1.createSHA1Code(action.getPass()));
        return benutzer;
    }
}
