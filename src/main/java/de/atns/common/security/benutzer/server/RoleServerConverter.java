package de.atns.common.security.benutzer.server;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import com.google.inject.extensions.security.SecurityRole;
import de.atns.common.security.client.model.SecurityRolePresentation;

import java.util.HashSet;
import java.util.Set;

import static com.google.inject.extensions.security.ClassHelper.resolveAll;

/**
 * @author mwolter
 * @since 08.12.11 12:46
 */
public class RoleServerConverter implements Converter<Class<? extends SecurityRole>, SecurityRolePresentation> {

    public static final Converter<Class<? extends SecurityRole>, SecurityRolePresentation> ROLE_CONVERTER =
            new RoleServerConverter();

    public static HashSet<SecurityRolePresentation> convert(Set<Class<? extends SecurityRole>> roles) {
        Set<Class<? extends SecurityRole>> allRoles = new HashSet<Class<? extends SecurityRole>>();
        for (Class<? extends SecurityRole> aClass : roles) {
            allRoles.addAll(resolveAll(aClass));
        }

        return new HashSet<SecurityRolePresentation>(Lambda.convert(allRoles, ROLE_CONVERTER));
    }

    @Override public SecurityRolePresentation convert(Class<? extends SecurityRole> from) {
        return new SecurityRolePresentation(from);
    }
}
