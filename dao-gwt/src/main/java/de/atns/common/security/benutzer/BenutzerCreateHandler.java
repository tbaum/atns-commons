package de.atns.common.security.benutzer;

import ch.lambdaj.function.convert.Converter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import de.atns.common.gwt.server.DefaultActionHandler;
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
public class BenutzerCreateHandler extends DefaultActionHandler<BenutzerCreate, UserPresentation> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(BenutzerCreateHandler.class);
    private final Provider<EntityManager> em;
    private final Converter<Benutzer, UserPresentation> converter;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerCreateHandler(final Provider<EntityManager> em, UserConverter converter) {
        super(BenutzerCreate.class);
        this.em = em;
        this.converter = converter;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    @Transactional
    @Secured(UserAdminRole.class)
    public UserPresentation executeInternal(final BenutzerCreate action) {
        UserPresentation p = action.getPresentation();

        try {
            final Benutzer result = new Benutzer(p.getLogin().toLowerCase(), createSHA1Code(p.getPasswort()),
                    p.getName(), p.getEmail());

            Set<Class<? extends SecurityRole>> roles = new HashSet<Class<? extends SecurityRole>>();
            for (SecurityRolePresentation presentation : p.getRoles()) {
                roles.add(presentation.getRole());
            }
            result.setRole(roles);

            em.get().persist(result);

            return converter.convert(result);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                LOG.warn(e, e);
                throw new RuntimeException("User mit diesem Login existiert bereits.");
            }
            throw e;
        }
    }
}
