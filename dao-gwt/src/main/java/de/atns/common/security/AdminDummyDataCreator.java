package de.atns.common.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import de.atns.common.security.client.model.UserAdminRole;
import de.atns.common.security.model.Benutzer;
import de.atns.common.util.SHA1;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * @author tbaum
 * @since 17.06.2010
 */
public class AdminDummyDataCreator {

    private final BenutzerRepository repository;
    private final Provider<EntityManager> entityManager;

    @Inject
    public AdminDummyDataCreator(final BenutzerRepository repository, final Provider<EntityManager> entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Transactional public void createDefaultAdmin() {
        final Benutzer admin = new Benutzer("admin", SHA1.createSHA1Code("admin"), "", "");
        admin.addRolle(UserAdminRole.class);
        entityManager.get().merge(admin);
    }

    public boolean hasDefaultAdmin() {
        try {
            repository.benutzerByLogin("admin");
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
