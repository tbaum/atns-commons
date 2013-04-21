package de.atns.common.security.benutzer;

import com.google.inject.Inject;
import com.google.inject.extensions.security.SecurityService;
import com.google.inject.extensions.security.SecurityUser;
import com.google.inject.persist.Transactional;
import de.atns.common.security.AbstractLoadUserDetailHandler;
import de.atns.common.security.BenutzerRepository;
import de.atns.common.security.benutzer.client.action.UserDetail;
import de.atns.common.security.benutzer.server.RoleServerConverter;
import de.atns.common.security.client.model.UserPresentation;
import de.atns.common.security.model.Benutzer;

/**
 * @author mwolter
 * @since 09.12.11 13:32
 */
public class UserLoadDetailHandler extends AbstractLoadUserDetailHandler<UserDetail, UserPresentation> {

    private final BenutzerRepository benutzerRepository;

    @Inject public UserLoadDetailHandler(SecurityService securityService, BenutzerRepository benutzerRepository) {
        super(securityService);
        this.benutzerRepository = benutzerRepository;
    }

    @Override protected Class<UserDetail> getActionClass() {
        return UserDetail.class;
    }

    @Override @Transactional
    protected UserPresentation loadUserDetail(SecurityUser securityUser) {
        final Benutzer benutzer = benutzerRepository.benutzerByLogin(securityUser.getLogin());

        return new UserPresentation(benutzer.getId(), benutzer.getLogin(), benutzer.getName(), benutzer.getPasswort(),
                benutzer.getEmail(), RoleServerConverter.convert(benutzer.getRoles()), benutzer.getLastLogin(),
                benutzer.getLastAccess());
    }
}
