package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.gwt.client.model.ListPresentation;

/**
 * @author mwolter
 * @since 22.09.2010 18:16:46
 */
public interface LoadListEventHandler<E extends IsSerializable> extends EventHandler {

    void onLoad(ListPresentation<E> event, Object source);
}
