package de.atns.common.security.benutzer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.extensions.security.UserService;
import com.google.inject.persist.Transactional;
import de.atns.common.security.BenutzerRepository;
import de.atns.common.security.model.Benutzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author tbaum
 * @since 27.11.2009
 */
@Singleton public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private final BenutzerRepository repository;

    @Inject public UserServiceImpl(final BenutzerRepository repository) {
        this.repository = repository;
    }

    public static String createSHA1Code(final String text) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes(), 0, text.length());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("NoSuchAlgorithmException: %s", e));
        }
    }

    @Override public Benutzer findUser(final String login, final String password) {
        final Benutzer user = findUser(login);
        if (user == null) {
            return null;
        }

        if (user.getPasswort().equals(createSHA1Code(password))) {
            LOG.info("login succeeded");
            return user;
        }
        LOG.error("invalid password for user '" + login + "'");
        return null;
    }

    @Override @Transactional public Benutzer findUser(final String login) {
        try {
            return repository.benutzerByLogin(login);
        } catch (NoResultException e) {
            LOG.error("user not found '" + login + "'");
        }
        return null;
    }
}
