package de.atns.common.security.client;

import com.google.gwt.core.client.GWT;
import de.atns.common.gwt.client.gin.SharedServices;

/**
 * @author tbaum
 * @since 05.10.2010
 */
public class SharedServicesHolder {
// ------------------------------ FIELDS ------------------------------

    private static final SharedServicesInjector instance = GWT.create(SharedServicesInjector.class);

// -------------------------- STATIC METHODS --------------------------

    public static SharedServicesInjector shared() {
        return instance;
    }

    public static void setSharedServices(SharedServices sharedServices) {
        instance.sharedServicesAware().setSharedServices(sharedServices);
    }
}
