package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.RequestFactory;
import net.customware.gwt.dispatch.client.DispatchAsync;

/**
 * @author tbaum
 * @since 14.06.2010
 */
public interface SharedServices {
// -------------------------- OTHER METHODS --------------------------

    DispatchAsync dispatcher();

    EventBus eventBus();

    RequestFactory requestFactory();
}
