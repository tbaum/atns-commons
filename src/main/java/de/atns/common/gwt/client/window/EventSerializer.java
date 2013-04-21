package de.atns.common.gwt.client.window;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;

/**
 * @author mwolter
 * @since 25.01.11 11:07
 */
public abstract class EventSerializer {
    protected boolean checkClass(Class aClass, String clazz) {
        return aClass.toString().equals(clazz);
    }

    public <T extends TransportAware> T deserialize(JSONArray jso) {
        final TransportAware result = createClass(jso.get(0).isString().stringValue());
        result.fromJson(jso.get(1));
        //noinspection unchecked
        return (T) result;
    }

    protected abstract TransportAware createClass(String clazz);

    public JavaScriptObject serialize(TransportAware a) {
        final JSONArray j = new JSONArray();
        j.set(0, new JSONString(a.getClass().toString()));
        j.set(1, a.toJson());

        return j.getJavaScriptObject();
    }
}
