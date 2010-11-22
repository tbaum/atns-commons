package de.atns.common.security.benutzer.server;

import com.google.inject.ImplementedBy;
import de.atns.common.security.model.Benutzer;

/**
 * @author tbaum
 * @since 13.09.2010
 */
@ImplementedBy(DefaultBenutzerRollenHandler.class)
public interface BenutzerRollenHandler {
// -------------------------- OTHER METHODS --------------------------

    void updateRollen(final Benutzer benutzer, final Object action);
}
