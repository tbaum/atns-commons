package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.EventBus;
import de.atns.common.security.RoleConverter;
import net.customware.gwt.dispatch.client.DispatchAsync;

/**
 * @author tbaum
 * @since 14.06.2010
 */
public interface SharedServices {
// -------------------------- OTHER METHODS --------------------------

    AppShell appShell();

    DispatchAsync dispatcher();

    EventBus eventBus();

    RoleConverter roleConverter();
}
