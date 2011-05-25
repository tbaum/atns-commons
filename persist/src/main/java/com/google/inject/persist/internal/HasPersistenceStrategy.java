package com.google.inject.persist.internal;

import com.google.inject.persist.PersistenceStrategy;

/**
 * Internal interface for passing persistence strategies around.
 * @author Robbie Vanbrabant
 */
public interface HasPersistenceStrategy {
    PersistenceStrategy getPersistenceStrategy();
}
