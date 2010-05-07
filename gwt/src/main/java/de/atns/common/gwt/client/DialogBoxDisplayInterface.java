package de.atns.common.gwt.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import org.cobogw.gwt.user.client.ui.Button;

/**
 * @author mwolter
 * @since 18.03.2010 14:52:56
 */
public interface DialogBoxDisplayInterface extends WidgetDisplay {
// -------------------------- OTHER METHODS --------------------------

    HandlerRegistration addCancelButtonClickHandler(ClickHandler clickHandler);

    Button getCancelButton();

    void hideDialogBox();

    void setCancelButtonText(String text);

    void setDialogBoxContent(String titel, Widget widget);

    void showDialogBox();

    HandlerRegistration addDialogBoxCloseCommand(CloseHandler<PopupPanel> handler);

    boolean isShowing();
}