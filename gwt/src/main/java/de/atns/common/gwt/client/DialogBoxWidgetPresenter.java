package de.atns.common.gwt.client;

import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import net.customware.gwt.presenter.client.EventBus;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public abstract class DialogBoxWidgetPresenter<D extends DialogBoxDisplayInterface> extends DefaultWidgetPresenter<D> {
// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public DialogBoxWidgetPresenter(D display, EventBus eventBus) {
        super(display, eventBus);
        display.setDialogBoxCloseCommand(new Command() {
            @Override public void execute() {
                unbind();
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