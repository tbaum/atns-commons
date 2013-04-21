package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author mwolter
 * @since 22.09.2010 18:16:46
 */
public interface LoadCreateEventHandler<E extends IsSerializable> extends EventHandler {

    void onCreate(E event, Object source);
}
