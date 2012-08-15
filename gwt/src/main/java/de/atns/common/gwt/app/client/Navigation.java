package de.atns.common.gwt.app.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import de.atns.common.gwt.client.DefaultWidgetDisplay;

/**
 * @author tbaum
 * @since 22.11.10
 */
public abstract class Navigation extends DefaultWidgetDisplay {

    @Inject private static PlaceController placeController;

    public abstract void setUsername(String s);

    protected class GoToPlaceCommand implements Command {
        private final Place place;

        public GoToPlaceCommand(final Place place) {
            this.place = place;
        }

        @Override public void execute() {
            placeController.goTo(place);
        }
    }
}
