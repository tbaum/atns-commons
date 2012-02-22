package de.atns.common.gwt.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author mwolter
 * @since 11.03.2010 17:31:11
 */
public class ConfirmButton extends Button {

    private final Button delButton;

    public ConfirmButton(final String buttonText, final String cancelText, final String message, final String title) {
        super(buttonText);
        delButton = new Button(buttonText);
        super.addClickHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                final DialogBox dialogBox = new DialogBox(false, true);
                dialogBox.setText(title);

                final FlowPanel flowPanel = new FlowPanel();

                final Label w = new Label(message, true);
                w.addStyleName("heading");
                w.getElement().getStyle().setPadding(15, Style.Unit.PX);
                flowPanel.add(w);

                final Button cancelButton = new Button(cancelText);
                cancelButton.getElement().getStyle().setPaddingLeft(10, Style.Unit.PX);

                final ClickHandler clickHandler = new ClickHandler() {
                    @Override public void onClick(final ClickEvent event) {
                        dialogBox.hide();
                    }
                };

                delButton.addClickHandler(clickHandler);
                cancelButton.addClickHandler(clickHandler);

                flowPanel.add(delButton);
                flowPanel.add(cancelButton);

                dialogBox.add(flowPanel);
                flowPanel.setWidth("500px");

                flowPanel.getElement().getStyle().setProperty("textAlign", "center");
                flowPanel.getElement().getStyle().setProperty("paddingBottom", "10px");

                dialogBox.setGlassEnabled(true);
                dialogBox.center();
                dialogBox.show();
            }
        });
    }

    @Override public HandlerRegistration addClickHandler(final ClickHandler handler) {
        return delButton.addClickHandler(handler);
    }
}
