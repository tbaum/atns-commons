package de.atns.common.security;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tbaum
 * @since 17.06.2010
 */
public class DummyDataCreator {

    private static final Logger LOG = LoggerFactory.getLogger(DummyDataCreator.class);

    @Inject public DummyDataCreator(final AdminDummyDataCreator ad) {
        if (!ad.hasDefaultAdmin()) {
            LOG.warn("no default admin found, creating user 'admin' / 'admin' ");
            ad.createDefaultAdmin();
        }
    }
}
