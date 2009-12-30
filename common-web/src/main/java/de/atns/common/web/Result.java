package de.atns.common.web;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import org.eclipse.jetty.util.ajax.JSON;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tbaum
 * @since 21.10.2009
 */
@RequestScoped public class Result {
// ------------------------------ FIELDS ------------------------------

    private final HttpServletRequest request;
    private final Map<String, Object> map = new LinkedHashMap<String, Object>();

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public Result(final HttpServletRequest request) {
        this.request = request;
    }

// -------------------------- OTHER METHODS --------------------------

    public void put(final String key, final Object value) {
        request.setAttribute(key, value);
        map.put(key, value);
    }

    public String toJSON() {
        return JSON.toString(map);
    }
}