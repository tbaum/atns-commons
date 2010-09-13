package de.atns.common.security.benutzer.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.Transactional;
import de.atns.common.gwt.client.model.CreateResult;
import de.atns.common.security.Secured;
import de.atns.common.security.benutzer.client.action.BenutzerCreate;
import de.atns.common.security.model.Benutzer;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;

import static de.atns.common.util.SHA1.createSHA1Code;
import static de.atns.common.security.model.DefaultRoles.ADMIN;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerCreateHandler implements ActionHandler<BenutzerCreate, CreateResult> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(BenutzerCreateHandler.class);
    private final Provider<EntityManager> em;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public BenutzerCreateHandler(final Provider<EntityManager> em) {
        this.em = em;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public Class<BenutzerCreate> getActionType() {
        return BenutzerCreate.class;
    }

    @Transactional @Secured(ADMIN)
    public CreateResult execute(final BenutzerCreate action, final ExecutionContext context) {
        final EntityManager em = this.em.get();
        try {
            Benutzer m = new Benutzer(action.getLogin().toLowerCase(), createSHA1Code(action.getPasswort()));

            em.persist(m);
            return new CreateResult(m.getLogin().hashCode());
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                LOG.warn(e, e);
                throw new RuntimeException("Benutzer mit diesem Login existiert bereits.");
            }
            throw e;
        }
    }

    @Override
    public void rollback(final BenutzerCreate action, final CreateResult result, final ExecutionContext context) {
    }
}