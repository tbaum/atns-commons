package de.atns.common.gwt.client.gin;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

/**
 * @author tbaum
 * @since 18.11.10
 */
public interface PlacePresenter<P extends Place> {

    Activity updateForPlace(P place);
}
