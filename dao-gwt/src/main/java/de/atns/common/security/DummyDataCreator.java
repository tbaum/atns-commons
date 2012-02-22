package de.atns.common.security;

import com.google.inject.Inject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author tbaum
 * @since 17.06.2010
 */
public class DummyDataCreator {

    private static final Log LOG = LogFactory.getLog(DummyDataCreator.class);

    @Inject public DummyDataCreator(final AdminDummyDataCreator ad) {
        if (!ad.hasDefaultAdmin()) {
            LOG.warn("no default admin found, creating user 'admin' / 'admin' ");
            ad.createDefaultAdmin();
        }
    }
}
