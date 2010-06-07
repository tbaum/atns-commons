package de.atns.common.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import org.cobogw.gwt.user.client.ui.Button;

import static com.google.gwt.dom.client.Style.Unit.PX;

/**
 * @author mwolter
 * @since 18.03.2010 14:30:10
 */
public abstract class DialogBoxErrorWidgetDisplay extends DefaultErrorWidgetDisplay implements DialogBoxDisplayInterface {
// ------------------------------ FIELDS ------------------------------

    private final DialogBox dialogBox = new DialogBox(true, false);
    private final Button cancelButton = new Button("Abbrechen");

// --------------------------- CONSTRUCTORS ---------------------------

    protected DialogBoxErrorWidgetDisplay() {
        super();
        cancelButton.addClickHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                hideDialogBox();
            }
        });
        cancelButton.getElement().getStyle().setMarginLeft(5, PX);
        cancelButton.getElement().getStyle().setMarginRight(5, PX);
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

    @Override public void showDialogBox() {
        dialogBox.center();
        dialogBox.show();
    }

    @Override public HandlerRegistration addDialogBoxCloseCommand(final CloseHandler<PopupPanel> handler) {
        return dialogBox.addCloseHandler(handler);
    }

    @Override public boolean isShowing() {
        return dialogBox.isShowing();
    }

// -------------------------- OTHER METHODS --------------------------

    public void setGlassEnabled(boolean enabled) {
        dialogBox.setGlassEnabled(enabled);
    }
}