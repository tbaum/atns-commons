package de.atns.common.web;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import org.json.JSONException;
import org.json.JSONStringer;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tbaum
 * @since 21.10.2009
 */
@RequestScoped public class Result {

    private final HttpServletRequest request;
    private final Map<String, Object> map = new LinkedHashMap<String, Object>();

    @Inject public Result(final HttpServletRequest request) {
        this.request = request;
    }

    public void put(final String key, final Object value) {
        request.setAttribute(key, value);
        map.put(key, value);
    }

    public String toJSON() {
        try {
            JSONStringer json = new JSONStringer();
            json.object();
            for (Map.Entry<String, Object> s : map.entrySet()) {
                json.key(s.getKey()).value(s.getValue());
            }
            json.endObject();
            return json.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
