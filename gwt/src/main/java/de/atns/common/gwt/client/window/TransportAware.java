package de.atns.common.gwt.client.window;

import com.google.gwt.json.client.JSONValue;

/**
 * @author: mwolter
 * @since: 25.01.11 11:12
 */
public interface TransportAware {
// -------------------------- OTHER METHODS --------------------------

    public void fromJson(JSONValue data);

    public JSONValue toJson();
}
