package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.GwtEvent;


/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class ModuleReadyEvent extends GwtEvent<ModuleReadyEventHandler> {
// ------------------------------ FIELDS ------------------------------

    private final SharedServicesModuleLoader sharedServicesModuleLoader;

// --------------------------- CONSTRUCTORS ---------------------------

    public ModuleReadyEvent(SharedServicesModuleLoader sharedServicesModuleLoader) {
        this.sharedServicesModuleLoader = sharedServicesModuleLoader;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void dispatch(final ModuleReadyEventHandler handler) {
        handler.onReady(sharedServicesModuleLoader);
    }

    @Override public Type<ModuleReadyEventHandler> getAssociatedType() {
        return ModuleReadyEventHandler.type;
    }
}
