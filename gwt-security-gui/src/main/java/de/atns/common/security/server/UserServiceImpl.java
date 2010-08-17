package de.atns.common.security.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.security.UserService;
import de.atns.common.security.model.Benutzer;
import de.atns.common.util.SHA1;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.NoResultException;

/**
 * @author tbaum
 * @since 27.11.2009
 */
@Singleton public class UserServiceImpl implements UserService {
// ------------------------------ FIELDS ------------------------------

    private static Log LOG = LogFactory.getLog(UserServiceImpl.class);
    private final BenutzerRepository repository;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public UserServiceImpl(final BenutzerRepository repository) {
        this.repository = repository;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface UserService ---------------------

    @Override public Benutzer findUser(final String login, final String pass) {
        Benutzer user = refreshUser(login);
        if (user == null)
            return null;

        if (user.getPasswort().equals(SHA1.createSHA1Code(pass))) {
            LOG.info("login succeeded");
            return user;
        }
        LOG.error("invalid password for user '" + login + "'");
        return null;
    }

    @Override public Benutzer refreshUser(final String login) {
        try {
            return repository.mitarbeiterByLogin(login);
        } catch (NoResultException e) {
            LOG.error("user not found '" + login + "'");
        }
        return null;
    }

//    private static Log LOG = LogFactory.getLog(UserServiceImpl.class);
//    private final AtnsAuthenticationManager auth;
//    private final Map<String, ProxyUser> userCache = new ConcurrentHashMap<String, ProxyUser>();
//    private final SecurityService securityService;
//
//
//
//    @Inject public UserServiceImpl(AtnsAuthenticationManager auth, final SecurityService securityService) {
//        this.auth = auth;
//        this.securityService = securityService;
//    }
//
//
//
//
//
//
//    @Override public ProxyUser findUser(final String login, final String pass) {
//        final ProxyUser proxyUser;
//
//        try {
//            Authentication a = auth.authenticateFrontend(new ClientAuthenticationToken(login, pass));
//            final ServerAuthenticationToken sa = (ServerAuthenticationToken) a;
//
//            final User user = (User) sa.getUser();
//            if (user == null) {
//                return null;
//            }
//
//            proxyUser = new ProxyUser(sa, user);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        updateContext(proxyUser);
//        userCache.put(login, proxyUser);
//
//        //     if (user.getPasswort().equals(SHA1.createSHA1Code(pass))) {
//        if (proxyUser.getPasswort().equals(pass)) {
//            LOG.info("login succeeded");
//            return proxyUser;
//        }
//        LOG.error("invalid password for user '" + login + "'");
//        return null;
//    }
//
//    @Override public ProxyUser refreshUser(final String login) {
//        return userCache.get(login);
//    }
//
//
//
//    void updateContext(final ProxyUser pu) {
//        if (pu != null) {
//            SecurityContextHolder.getContext().setAuthentication(pu.getAuth());
//        } else {
//            SecurityContextHolder.clearContext();
//        }
//    }
//
//
//
//    public static class ProxyUser extends User implements SecurityUser {
//        private final ServerAuthenticationToken auth;
//        private final User user;
//
//        public ProxyUser(final ServerAuthenticationToken auth, final User user) {
//            this.auth = auth;
//            this.user = user;
//        }
//
//        @Override public String getPasswort() {
//            return user.getPasswort();
//        }
//
//        @Override public String getLogin() {
//            return user.getLogin();
//        }
//
//        @Override public boolean hasAccessTo(final Secured secured) {
//            return user.hasAccessTo(secured);
//        }
//
//        public ServerAuthenticationToken getAuth() {
//            return auth;
//        }
//
//        @Override public Long getId() {
//            return user.getId();
//        }
//    }
}
