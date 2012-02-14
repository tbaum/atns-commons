package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.gwt.client.model.ListPresentation;

import java.io.Serializable;

/**
 * @author mwolter
 * @since 22.09.2010 18:16:46
 */
public interface LoadListEventHandler<E extends Serializable> extends EventHandler {
// -------------------------- OTHER METHODS --------------------------

    void onLoad(ListPresentation<E> event, Object source);
}
