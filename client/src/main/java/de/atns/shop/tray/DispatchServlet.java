package de.atns.shop.tray;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import de.atns.shop.tray.data.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Singleton public class DispatchServlet extends HttpServlet {
// ------------------------------ FIELDS ------------------------------

    private final Map<String, Provider<Action>> actionMap;
    private final AuthtokenCache cache;
    private final Provider<Result> result;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public DispatchServlet(final Map<String, Provider<Action>> actionMap, final AuthtokenCache cache, final Provider<Result> result) {
        this.actionMap = actionMap;
        this.cache = cache;
        this.result = result;
    }

// -------------------------- OTHER METHODS --------------------------

    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        cache.updateTokenCache(request);

        final String path = request.getPathInfo();
        final Provider<Action> actionProvider = actionMap.get(path);
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
}