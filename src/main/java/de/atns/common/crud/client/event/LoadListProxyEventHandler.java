package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.web.bindery.requestfactory.shared.EntityProxy;

import java.util.List;

/**
 * @author mwolter
 * @since 22.09.2010 18:16:46
 */
public interface LoadListProxyEventHandler<E extends EntityProxy> extends EventHandler {

    void onLoad(List<E> event, Object source);
}
