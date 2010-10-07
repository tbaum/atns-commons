package de.atns.common.gwt.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.presenter.client.Presenter;

/**
 * @author mwolter
 * @since 05.03.2010 11:48:13
 */
public interface ListPresenter<T extends IsSerializable> extends Presenter {

    void updateList();
}