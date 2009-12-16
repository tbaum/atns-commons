package de.atns.common.security;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.security.client.SecurityUser;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static de.atns.common.security.AuthenticateFilter.SESSION_UUID;

/**
 * @author tbaum
 * @since 16.12.2009
 */
@Singleton public class SecurityService {
// ------------------------------ FIELDS ------------------------------

    private final SecurityScope securityScope;
    private final UserService userService;
    private final Map<UUID, SecurityUserToken> cache = new HashMap<UUID, SecurityUserToken>();

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public SecurityService(final UserService userService, final SecurityScope securityScope) {
        this.userService = userService;
        this.securityScope = securityScope;
    }

// -------------------------- OTHER METHODS --------------------------

    public void authFromHeader(final ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            final HttpServletRequest servletRequest = (HttpServletRequest) request;
            final String uuid = servletRequest.getHeader("X-Authorization");
            authFromUuid(uuid);
        }
    }

    private void authFromUuid(final String uuid) {
        if (uuid != null && !uuid.isEmpty()) {
            final SecurityUserToken user = cache.get(UUID.fromString(uuid));
            if (user != null) {
                securityScope.put(SecurityUser.class, user.getUser());
            }
        }
    }

    public void authFromSession(final ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            final HttpServletRequest servletRequest = (HttpServletRequest) request;

            final HttpSession session = servletRequest.getSession(false);
            if (session != null) {
                authFromUuid((String) session.getAttribute(SESSION_UUID));
            }
        }
    }

    public SecurityUserToken login(final String login, final String password) {
        final SecurityUser user = userService.findUser(login, password);
        final SecurityUserToken token = new SecurityUserToken(user);
        cache.put(token.uuid, token);
        return token;
    }

// -------------------------- INNER CLASSES --------------------------

    public class SecurityUserToken {
        private final SecurityUser user;
        private final UUID uuid = UUID.randomUUID();

        public SecurityUserToken(final SecurityUser user) {
            this.user = user;
        }

        public SecurityUser getUser() {
            return user;
        }

        public UUID getUuid() {
            return uuid;
        }
    }
}
