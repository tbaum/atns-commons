package de.atns.common.security.benutzer.client.gin;

import de.atns.common.crud.client.CrudModule;
import de.atns.common.security.benutzer.client.*;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class BenutzerModule extends CrudModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configure() {
        super.configure();

        bindPresenter(BenutzerChangePasswordPresenter.class, BenutzerChangePasswordPresenter.Display.class,
                BenutzerChangePasswordView.class);
        bindPresenter(BenutzerPresenter.class, BenutzerPresenter.Display.class, BenutzerView.class);
        bindPresenter(BenutzerCreatePresenter.class, BenutzerCreatePresenter.Display.class, BenutzerDetailView.class);
        bindPresenter(BenutzerEditPresenter.class, BenutzerEditPresenter.Display.class, BenutzerDetailView.class);
    }
}
