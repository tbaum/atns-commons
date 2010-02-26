package de.atns.common.security;

import com.google.inject.ImplementedBy;
import de.atns.common.security.SecurityUser;

import java.util.UUID;

/**
 * @author tbaum
 * @since 16.12.2009
 */
@ImplementedBy(SecurityServiceImpl.class)
public interface SecurityService {
// -------------------------- OTHER METHODS --------------------------

    public SecurityUser authenticate(final UUID uuid);

    public UUID login(final String login, final String password);

    public SecurityUser logout();
}
