package de.atns.common.crud.client;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import de.atns.common.gwt.client.DialogBoxDisplayInterface;

/**
 * Created by IntelliJ IDEA.
 * User: tbaum
 * Date: 05.10.2010
 * Time: 04:07:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class DialogBoxListWidgetPresenter<D extends DialogBoxDisplayInterface & ListWidgetDisplay>
        extends ListWidgetPresenter<D> {
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
