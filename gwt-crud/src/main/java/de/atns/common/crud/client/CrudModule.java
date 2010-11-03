package de.atns.common.crud.client;

import de.atns.common.security.client.SharedServicesModule;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class CrudModule extends SharedServicesModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configure() {
        super.configure();

        bindDisplay(PagePresenter.Display.class, PageView.class);
    }
}
