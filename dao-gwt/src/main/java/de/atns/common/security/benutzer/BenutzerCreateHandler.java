package de.atns.common.security.benutzer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import de.atns.common.gwt.server.ConvertingActionHandler;
import de.atns.common.security.Secured;
import de.atns.common.security.SecurityRole;
import de.atns.common.security.SecurityRolePresentation;
import de.atns.common.security.benutzer.client.action.BenutzerCreate;
import de.atns.common.security.client.model.UserAdminRole;
import de.atns.common.security.client.model.UserPresentation;
import de.atns.common.security.model.Benutzer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

import static de.atns.common.util.SHA1.createSHA1Code;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerCreateHandler extends ConvertingActionHandler<BenutzerCreate, UserPresentation, Benutzer> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(BenutzerCreateHandler.class);
    private final Provider<EntityManager> em;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public BenutzerCreateHandler(final Provider<EntityManager> em) {
        super(UserConverter.USER_CONVERTER, BenutzerCreate.class);
        this.em = em;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override @Secured(UserAdminRole.class) @Transactional
    public Benutzer executeInternal(final BenutzerCreate action) {
        final EntityManager em = this.em.get();
        UserPresentation p = action.getPresentation();

        try {
            final Benutzer m = new Benutzer(p.getLogin().toLowerCase(), createSHA1Code(p.getPasswort()), p.getName(),
                    p.getEmail());

            Set<Class<? extends SecurityRole>> roles = new HashSet<Class<? extends SecurityRole>>();
            for (SecurityRolePresentation presentation : p.getRoles()) {
                roles.add(presentation.getRole());
            }
            m.setRole(roles);

            em.persist(m);
            return m;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                LOG.warn(e, e);
                throw new RuntimeException("User mit diesem Login existiert bereits.");
            }
            throw e;
        }
    }
}
