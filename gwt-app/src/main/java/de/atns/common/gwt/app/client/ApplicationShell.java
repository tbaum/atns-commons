package de.atns.common.gwt.app.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author tbaum
 * @since 05.11.10
 */
public class ApplicationShell extends Composite {
// ------------------------------ FIELDS ------------------------------

    @UiField HasOneWidget contentPanel;
    @UiField(provided = true) final Navigation navigation;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public ApplicationShell(final Navigation navigation) {
        this.navigation = navigation;

        final ShellUiBinder uiBinder = GWT.create(ShellUiBinder.class);
        initWidget(uiBinder.createAndBindUi(this));
    }

// -------------------------- OTHER METHODS --------------------------

    public HasOneWidget getPanel() {
        return contentPanel;
    }

// -------------------------- INNER CLASSES --------------------------

    interface ShellUiBinder extends UiBinder<Widget, ApplicationShell> {
    }
}
