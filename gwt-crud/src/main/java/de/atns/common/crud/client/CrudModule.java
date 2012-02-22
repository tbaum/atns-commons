package de.atns.common.crud.client;

import de.atns.common.crud.client.event.LoadCreateEvent;
import de.atns.common.crud.client.event.LoadDetailEvent;
import de.atns.common.crud.client.event.LoadListEvent;
import de.atns.common.crud.client.event.LoadListProxyEvent;
import de.atns.common.security.client.SharedServicesModule;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public class CrudModule extends SharedServicesModule {

    @Override protected void configure() {
        super.configure();

        bindDisplay(PageProxyPresenter.Display.class, PageProxyView.class);
        bindDisplay(PagePresenter.Display.class, PageView.class);

        requestStaticInjection(LoadCreateEvent.class);
        requestStaticInjection(LoadDetailEvent.class);
        requestStaticInjection(LoadListEvent.class);
        requestStaticInjection(LoadListProxyEvent.class);
    }
}
