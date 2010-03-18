package de.atns.common.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import de.atns.common.gwt.client.DialogBoxDisplayInterface;
import org.cobogw.gwt.user.client.ui.Button;

/**
 * @author mwolter
 * @since 18.03.2010 14:30:10
 */
public abstract class DialogBoxDisplay extends DefaultDisplay implements DialogBoxDisplayInterface {
// ------------------------------ FIELDS ------------------------------

    private final DialogBox dialogBox = new DialogBox(true, false);
    private final Button cancelButton = new Button("Abbrechen");

// --------------------------- CONSTRUCTORS ---------------------------

    protected DialogBoxDisplay() {
        super();
        dialogBox.setGlassEnabled(true);
        cancelButton.addClickHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                hideDialogBox();
            }
        });
    }

    @Override public void hideDialogBox() {
        dialogBox.hide();
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override public Button getCancelButton() {
        return cancelButton;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DialogBoxDisplayInterface ---------------------

    @Override public HandlerRegistration addCancelButtonClickHandler(ClickHandler clickHandler) {
        return cancelButton.addClickHandler(clickHandler);
    }

    @Override public void setCancelButtonText(final String text) {
        cancelButton.setText(text);
    }

    @Override public void setDialogBoxContent(final String titel, final Widget widget) {
        dialogBox.setText(titel);
        dialogBox.setWidget(widget);
    }

    @Override public void showDialogBox(final Command command) {
        dialogBox.center();
        dialogBox.show();
        dialogBox.addCloseHandler(new CloseHandler<PopupPanel>() {
            @Override public void onClose(final CloseEvent<PopupPanel> popupPanelCloseEvent) {
                command.execute();
            }
        });
    }
}