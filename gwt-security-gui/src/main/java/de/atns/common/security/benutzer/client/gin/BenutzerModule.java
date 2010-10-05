package de.atns.common.security.benutzer.client.gin;

import de.atns.common.crud.client.SharedServicesModule;
import de.atns.common.security.benutzer.client.*;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerModule extends SharedServicesModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configure() {
        super.configure();

        bindPresenter(BenutzerPresenter.class, BenutzerPresenter.Display.class, BenutzerView.class);
        bindPresenter(BenutzerCreatePresenter.class, BenutzerCreatePresenter.Display.class, BenutzerCreateView.class);
        bindPresenter(BenutzerEditPresenter.class, BenutzerEditPresenter.Display.class, BenutzerEditView.class);
        bindPresenter(BenutzerChangePasswordPresenter.class, BenutzerChangePasswordPresenter.Display.class,
                BenutzerChangePasswordView.class);
    }
}