package de.atns.common.web;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author tbaum
 * @since 21.10.2009
 */
@Singleton
public class DispatchServlet extends HttpServlet {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -8267730268526925897L;
    private final Map<String, Provider<Action>> actionMap;
    private final Provider<Result> result;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public DispatchServlet(final Map<String, Provider<Action>> actionMap, final Provider<Result> result) {
        this.actionMap = actionMap;
        this.result = result;
    }

    // -------------------------- OTHER METHODS --------------------------

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String path = request.getPathInfo();
        final Provider<Action> actionProvider = lookupProvider(path);
        if (actionProvider == null) {
            throw new ServletException("no Provider<Action> for request-path '" + path + "' found!");
        }

        final Action action = actionProvider.get();
        if (action == null) {
            throw new ServletException("Unable to create Action for request-path '" + path + "'");
        }

        action.service();
        result.get().put("success", true);
    }

    private Provider<Action> lookupProvider(final String path) {
        for (final Map.Entry<String, Provider<Action>> stringProviderEntry : actionMap.entrySet()) {
            if (path.matches(stringProviderEntry.getKey())) {
                return stringProviderEntry.getValue();
            }
        }
        return null;
    }
}