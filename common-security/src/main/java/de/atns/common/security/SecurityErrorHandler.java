package de.atns.common.security;

import de.atns.common.security.client.SecurityUser;

import java.util.List;

/**
 * @author Michael Hunger
 * @since 24.01.2010
 */
public interface SecurityErrorHandler {
    void notLoggedIn();
    void notInRole(SecurityUser user, List<String> roles);
}
