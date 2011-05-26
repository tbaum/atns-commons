package de.atns.common.dao;


import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

/**
 * @author tbaum
 * @since 26.05.11 05:17
 */
public class JpaInitializer {
// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public JpaInitializer(PersistService persistService) {
        persistService.start();
    }
}
