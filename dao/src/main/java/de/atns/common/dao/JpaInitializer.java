package de.atns.common.dao;


import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author tbaum
 * @since 26.05.11 05:17
 */
public class JpaInitializer {
// ------------------------------ FIELDS ------------------------------

    private final static Log LOG = LogFactory.getLog(JpaInitializer.class);

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public JpaInitializer(PersistService persistService) {
        try {
            persistService.start();
        } catch (RuntimeException e) {
            LOG.error(e, e);
            throw e;
        }
    }
}
