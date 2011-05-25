package de.atns.common.security.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
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
@Singleton
public class UserServiceImpl implements UserService {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);
    private final BenutzerRepository repository;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public UserServiceImpl(final BenutzerRepository repository) {
        this.repository = repository;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface UserService ---------------------

    @Override public Benutzer findUser(final String login, final String pass) {
        final Benutzer user = refreshUser(login);
        if (user == null) {
            return null;
        }

        if (user.getPasswort().equals(SHA1.createSHA1Code(pass))) {
            LOG.info("login succeeded");
            return user;
        }
        LOG.error("invalid password for user '" + login + "'");
        return null;
    }

    @Override @Transactional public Benutzer refreshUser(final String login) {
        try {
            return repository.benutzerByLogin(login);
        } catch (NoResultException e) {
            LOG.error("user not found '" + login + "'");
        }
        return null;
    }
}
