package de.atns.common.gwt.client;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.crud.client.event.LoadListEventHandler;
import net.customware.gwt.presenter.client.Presenter;

/**
 * @author mwolter
 * @since 05.03.2010 11:48:13
 */
public interface ListPresenter<T extends IsSerializable> extends Presenter {
// -------------------------- OTHER METHODS --------------------------

    GwtEvent.Type<LoadListEventHandler<T>> _listEvent();

    void updateList();
}