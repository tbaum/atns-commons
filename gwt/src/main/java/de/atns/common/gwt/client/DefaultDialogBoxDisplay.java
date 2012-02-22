package de.atns.common.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import static com.google.gwt.dom.client.Style.Unit.PX;

/**
 * @author mwolter
 * @since 18.03.2010 14:30:10
 */
public abstract class DefaultDialogBoxDisplay extends DefaultWidgetDisplay
        implements DialogBoxDisplay {

    private final DialogBox dialogBox = new DialogBox(false, false);
    private final Button cancelButton = new Button("Abbrechen");

    protected DefaultDialogBoxDisplay() {
        super();
        setGlassEnabled(true);
        cancelButton.addClickHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                hideDialogBox();
            }
        });
        cancelButton.getElement().getStyle().setMarginLeft(5, PX);
        cancelButton.getElement().getStyle().setMarginRight(5, PX);
    }

    public void setGlassEnabled(final boolean enabled) {
        dialogBox.setGlassEnabled(enabled);
    }

    @Override public void hideDialogBox() {
        dialogBox.hide();
    }

    @Override public Button getCancelButton() {
        return cancelButton;
    }

    @Override public void setTitle(String titel) {
        dialogBox.setText(titel);
    }

    @Override public HandlerRegistration addCancelButtonClickHandler(final ClickHandler clickHandler) {
        return cancelButton.addClickHandler(clickHandler);
    }

    @Override public HandlerRegistration addDialogBoxCloseCommand(final CloseHandler<PopupPanel> handler) {
        return dialogBox.addCloseHandler(handler);
    }

    @Override public boolean isShowing() {
        return dialogBox.isShowing();
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
}
