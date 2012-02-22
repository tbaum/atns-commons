package de.atns.common.gwt.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.event.shared.EventBus;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public abstract class DialogBoxPresenter<D extends DialogBoxDisplay> extends WidgetPresenter<D> implements Command {

    private final Logger LOG = Logger.getLogger(this.getClass().toString());

    @Override public void execute() {
        bind();
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

    @Override public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        LOG.log(Level.FINE, "Activity: start()");
        bind();
    }
}
