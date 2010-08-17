package de.atns.common.security.benutzer.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.Transactional;
import de.atns.common.gwt.client.model.EmptyResult;
import de.atns.common.security.Secured;
import de.atns.common.security.SecurityService;
import de.atns.common.security.model.Benutzer;
import de.atns.common.util.SHA1;
import de.atns.common.security.benutzer.client.action.BenutzerChangePassword;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerChangePasswordHandler implements ActionHandler<BenutzerChangePassword, EmptyResult> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(BenutzerChangePasswordHandler.class);
    private final Provider<EntityManager> em;
    private final SecurityService securityService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerChangePasswordHandler(final Provider<EntityManager> em, final SecurityService securityService) {
        this.em = em;
        this.securityService = securityService;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public Class<BenutzerChangePassword> getActionType() {
        return BenutzerChangePassword.class;
    }

    @Transactional @Secured @Override
    public EmptyResult execute(final BenutzerChangePassword action, ExecutionContext context) {

        final Benutzer t = (Benutzer) securityService.currentUser();

        Benutzer m = em.get().find(Benutzer.class, t.getId());
        m.setPasswort(SHA1.createSHA1Code(action.getPass()));

        //  em.merge(m);
        return new EmptyResult();
    }

    @Override
    public void rollback(final BenutzerChangePassword action, final EmptyResult result, final ExecutionContext context) {
    }
}