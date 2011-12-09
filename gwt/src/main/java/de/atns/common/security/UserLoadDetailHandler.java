package de.atns.common.security;

import com.google.inject.Inject;
import de.atns.common.security.benutzer.server.RoleServerConverter;
import de.atns.common.security.client.action.UserDetail;
import de.atns.common.security.client.model.UserPresentation;
import de.atns.common.security.model.Benutzer;

/**
 * @author mwolter
 * @since 09.12.11 13:32
 */
public class UserLoadDetailHandler extends AbstractLoadUserDetailHandler<UserDetail, UserPresentation> {
// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public UserLoadDetailHandler(SecurityService securityService) {
        super(UserDetail.class, securityService);
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected UserPresentation loadUserDetail(SecurityUser securityUser) {
        return RoleServerConverter.USER_CONVERTER_RESOLVED.convert((Benutzer) securityUser);
    }
}
