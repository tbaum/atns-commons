package de.atns.common.gwt.app.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import de.atns.common.security.client.login.LoginPlace;

public class NavigationNoAuth extends Composite {

    private final PlaceController placeController;
    @UiField MenuItem login;

    @Inject public NavigationNoAuth(final PlaceController placeController) {
        this.placeController = placeController;

        final ShellUiBinder uiBinder = GWT.create(ShellUiBinder.class);
        initWidget(uiBinder.createAndBindUi(this));

        login.setScheduledCommand(new GoToPlaceCommand(LoginPlace.ALL));
    }

    interface ShellUiBinder extends UiBinder<Widget, NavigationNoAuth> {
    }

    private class GoToPlaceCommand implements Command {
        private final Place place;

        public GoToPlaceCommand(final Place place) {
            this.place = place;
        }

        @Override public void execute() {
            placeController.goTo(place);
        }
    }
}
