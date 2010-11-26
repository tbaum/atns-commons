package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.gwt.client.model.ListPresentation;

import java.util.List;

/**
 * @author mwolter
 * @since 22.09.2010 18:16:46
 */
public interface LoadListProxyEventHandler<E extends EntityProxy> extends EventHandler {
// -------------------------- OTHER METHODS --------------------------

    void onLoad(List<E> event, Object source);
}
