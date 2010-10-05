package de.atns.common.security.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import de.atns.common.gwt.client.gin.SharedServicesAware;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

/**
 * @author tbaum
 * @since 13.06.2010
 */
@GinModules(SharedServicesModule.class)
public interface SharedServicesInjector extends Ginjector {
// -------------------------- OTHER METHODS --------------------------

    DispatchAsync getDispatchAsync();

    EventBus getEventBus();

    SharedServicesAware sharedServicesAware();
}