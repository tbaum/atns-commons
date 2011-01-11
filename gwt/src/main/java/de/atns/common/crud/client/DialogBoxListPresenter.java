package de.atns.common.crud.client;

import com.google.gwt.core.client.Scheduler;
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
// -------------------------- OTHER METHODS --------------------------

    @Override public D getDisplay() {
        return super.getDisplay();
    }

    @Override protected void onBind() {
        super.onBind();

        display.addDialogBoxCloseCommand(new CloseHandler<PopupPanel>() {
            @Override public void onClose(final CloseEvent<PopupPanel> popupPanelCloseEvent) {
                unbind();
            }
        });

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override public void execute() {
                if (isBound()) {
                    display.showDialogBox();
                }
            }
        });
    }

    @Override public void unbind() {
        super.unbind();
        if (display.isShowing()) {
            display.hideDialogBox();
        }
    }
}
