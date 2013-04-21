package de.atns.common.gwt.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author mwolter
 * @since 18.03.2010 14:52:56
 */
public interface DialogBoxDisplay extends WidgetDisplay {

    HandlerRegistration addCancelButtonClickHandler(ClickHandler clickHandler);

    HandlerRegistration addDialogBoxCloseCommand(CloseHandler<PopupPanel> handler);

    Button getCancelButton();

    void hideDialogBox();

    boolean isShowing();

    void setCancelButtonText(String text);

    void setDialogBoxContent(String titel, Widget widget);

    void setTitle(String titel);

    void showDialogBox();
}
