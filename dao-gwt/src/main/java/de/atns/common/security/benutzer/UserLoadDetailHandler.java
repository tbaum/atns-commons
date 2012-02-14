package de.atns.common.security.benutzer;

import com.google.inject.Inject;
import de.atns.common.security.AbstractLoadUserDetailHandler;
import de.atns.common.security.SecurityService;
import de.atns.common.security.SecurityUser;
import de.atns.common.security.benutzer.client.action.UserDetail;
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
        return UserConverter.USER_CONVERTER_RESOLVED.convert((Benutzer) securityUser);
    }
}
