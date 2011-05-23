package de.atns.common.security.benutzer.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import de.atns.common.gwt.server.ConvertingActionHandler;
import de.atns.common.security.Secured;
import de.atns.common.security.benutzer.client.action.BenutzerCreate;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import de.atns.common.security.model.Benutzer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;

import static de.atns.common.security.model.DefaultRoles.ADMIN;
import static de.atns.common.util.SHA1.createSHA1Code;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerCreateHandler extends ConvertingActionHandler<BenutzerCreate, BenutzerPresentation, Benutzer> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(BenutzerCreateHandler.class);

    public final BenutzerRollenHandler roleHandler;
    private final Provider<EntityManager> em;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public BenutzerCreateHandler(final Provider<EntityManager> em, final BenutzerRollenHandler roleHandler) {
        super(BenutzerPresentationConverter.BENUTZER_CONVERTER, BenutzerCreate.class);
        this.em = em;
        this.roleHandler = roleHandler;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override @Transactional @Secured(ADMIN) public Benutzer executeInternal(final BenutzerCreate action) {
        final EntityManager em = this.em.get();
        try {
            final Benutzer m = new Benutzer(action.getLogin().toLowerCase(), createSHA1Code(action.getPasswort()),
                    action.getEmail().toLowerCase());
            roleHandler.updateRollen(m, action);

            em.persist(m);
            return m;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                LOG.warn(e, e);
                throw new RuntimeException("Benutzer mit diesem Login existiert bereits.");
            }
            throw e;
        }
    }
}
