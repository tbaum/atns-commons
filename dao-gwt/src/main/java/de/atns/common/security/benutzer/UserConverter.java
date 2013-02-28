package de.atns.common.security.benutzer;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import de.atns.common.security.client.model.SecurityRolePresentation;
import de.atns.common.security.benutzer.server.RoleServerConverter;
import de.atns.common.security.client.model.UserPresentation;
import de.atns.common.security.model.Benutzer;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author mwolter
 * @since 14.02.12 19:02
 */
public class UserConverter implements Converter<Benutzer, UserPresentation> {

    @Override public UserPresentation convert(Benutzer b) {
        Date lastAccess = b.getLastAccess();
        if (lastAccess != null && lastAccess.before(new Date(new Date().getTime() - 60000))) {
            lastAccess = null;
        }

        final List<SecurityRolePresentation> r = Lambda.convert(b.getRoles(), RoleServerConverter.ROLE_CONVERTER);
        return new UserPresentation(b.getId(), b.getLogin(), b.getName(), b.getPasswort(),
                b.getEmail(), new HashSet<SecurityRolePresentation>(r), b.getLastLogin(), lastAccess);
    }
}
