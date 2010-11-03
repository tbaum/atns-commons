package de.atns.common.security.benutzer.server;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.Transactional;
import de.atns.common.gwt.server.ConvertingActionHandler;
import de.atns.common.security.Secured;
import de.atns.common.security.benutzer.client.action.BenutzerUpdate;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import de.atns.common.security.model.Benutzer;
import de.atns.common.util.SHA1;
import net.customware.gwt.dispatch.shared.ActionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;

import static de.atns.common.security.benutzer.server.BenutzerPresentationConverter.BENUTZER_CONVERTER;
import static de.atns.common.security.model.DefaultRoles.ADMIN;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerUpdateHandler extends ConvertingActionHandler<BenutzerUpdate, BenutzerPresentation, Benutzer> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(BenutzerUpdateHandler.class);
    private final Provider<EntityManager> em;
    private final BenutzerRollenHandler roleHandler;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public BenutzerUpdateHandler(final Provider<EntityManager> em, final BenutzerRollenHandler roleHandler) {
        super(BENUTZER_CONVERTER);
        this.em = em;
        this.roleHandler = roleHandler;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public Class<BenutzerUpdate> getActionType() {
        return BenutzerUpdate.class;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override @Transactional @Secured(ADMIN)
    public Benutzer executeInternal(final BenutzerUpdate action) throws ActionException {
        final EntityManager em = this.em.get();

        final Benutzer benutzer = em.find(Benutzer.class, action.getId());
        roleHandler.updateRollen(benutzer, action);
        benutzer.setEmail(action.getEmail().toLowerCase());

        if (!action.getPasswort().isEmpty()) {
            benutzer.setPasswort(SHA1.createSHA1Code(action.getPasswort()));
        }
        return benutzer;
    }
}
