package de.atns.common.crud.client;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.PopupPanel;
import de.atns.common.gwt.client.DialogBoxDisplay;

/**
 * @author tbaum
 * @since 05.10.2010
 */
public abstract class DialogBoxListPresenter<D extends DialogBoxDisplay & ListDisplay<T>, T extends IsSerializable>
        extends ListPresenter<D, T> {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Presenter ---------------------

    @Override public D getDisplay() {
        return super.getDisplay();
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void onBind() {
        super.onBind();

        display.addDialogBoxCloseCommand(new CloseHandler<PopupPanel>() {
            @Override
            public void onClose(final CloseEvent<PopupPanel> popupPanelCloseEvent) {
                unbind();
            }
        });
    }

    @Override
    public void unbind() {
        super.unbind();
        if (display.isShowing()) {
            display.hideDialogBox();
        }
    }
}
