package de.atns.common.security.benutzer.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.Transactional;
import de.atns.common.gwt.client.model.EmptyResult;
import de.atns.common.security.Secured;
import de.atns.common.security.benutzer.client.action.BenutzerUpdate;
import de.atns.common.security.model.Benutzer;
import de.atns.common.util.SHA1;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;

import static de.atns.common.security.model.DefaultRoles.ADMIN;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerUpdateHandler implements ActionHandler<BenutzerUpdate, EmptyResult> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(BenutzerUpdateHandler.class);
    private final Provider<EntityManager> em;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public BenutzerUpdateHandler(final Provider<EntityManager> em) {
        this.em = em;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public Class<BenutzerUpdate> getActionType() {
        return BenutzerUpdate.class;
    }

    @Transactional @Secured(ADMIN)
    public EmptyResult execute(final BenutzerUpdate action, ExecutionContext context) throws ActionException {
        final EntityManager em = this.em.get();

        Benutzer benutzer = em.find(Benutzer.class, action.getLogin());

        if (action.isAdmin()) {
            benutzer.addRolle(ADMIN);
        } else {
            benutzer.removeRolle(ADMIN);
        }

        benutzer.setEmail(action.getEmail());

        if (!action.getPasswort().isEmpty()) {
            benutzer.setPasswort(SHA1.createSHA1Code(action.getPasswort()));
        }
        return new EmptyResult();
    }

    @Override
    public void rollback(final BenutzerUpdate action, final EmptyResult result, final ExecutionContext context) {
    }
}