package de.atns.common.security.benutzer.server;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;
import de.atns.common.security.SecurityRole;
import de.atns.common.security.SecurityRolePresentation;
import de.atns.common.security.client.model.UserPresentation;
import de.atns.common.security.model.Benutzer;

import java.util.*;

/**
 * @author mwolter
 * @since 08.12.11 12:46
 */
public class RoleServerConverter implements Converter<Class<? extends SecurityRole>, SecurityRolePresentation> {
// ------------------------------ FIELDS ------------------------------

    public static final Converter<Class<? extends SecurityRole>, SecurityRolePresentation> ROLE_CONVERTER =
            new RoleServerConverter();

    public static Converter<Benutzer, UserPresentation> USER_CONVERTER = new Converter<Benutzer, UserPresentation>() {
        @Override public UserPresentation convert(Benutzer b) {
            Date lastAccess = b.getLastAccess();
            if (lastAccess != null && lastAccess.before(new Date(new Date().getTime() - 60000))) {
                lastAccess = null;
            }

            final List<SecurityRolePresentation> r = Lambda.convert(b.getRoles(), RoleServerConverter.ROLE_CONVERTER);
            return new UserPresentation(b.getId(), b.getLogin(), b.getName(), b.getPasswort(),
                    b.getEmail(), new HashSet<SecurityRolePresentation>(r), b.getLastLogin(), lastAccess);
        }
    };
    public static Converter<Benutzer, UserPresentation> USER_CONVERTER_RESOLVED = new Converter<Benutzer, UserPresentation>() {
        @Override public UserPresentation convert(Benutzer b) {
            return new UserPresentation(b.getId(), b.getLogin(), b.getName(), b.getPasswort(),
                    b.getEmail(), RoleServerConverter.convert(b.getRoles()), b.getLastLogin(), b.getLastAccess());
        }
    };

    private static Map<Class<? extends SecurityRole>, Set<Class<? extends SecurityRole>>> cache = new HashMap<Class<? extends SecurityRole>, Set<Class<? extends SecurityRole>>>();

// -------------------------- STATIC METHODS --------------------------

    public static HashSet<SecurityRolePresentation> convert(Set<Class<? extends SecurityRole>> roles) {
        Set<Class<? extends SecurityRole>> allRoles = new HashSet<Class<? extends SecurityRole>>();
        for (Class<? extends SecurityRole> aClass : roles) {
            allRoles.addAll(resolveAll(aClass));
        }

        return new HashSet<SecurityRolePresentation>(Lambda.convert(allRoles, RoleServerConverter.ROLE_CONVERTER));
    }

    public static Set<Class<? extends SecurityRole>> resolveAll(Class<? extends SecurityRole> aClass) {
        if (cache.containsKey(aClass)) return cache.get(aClass);

        final Set<Class<? extends SecurityRole>> classes = resolveAll(aClass,
                new HashSet<Class<? extends SecurityRole>>());
        cache.put(aClass, classes);
        return classes;
    }

    private static Set<Class<? extends SecurityRole>> resolveAll(Class<? extends SecurityRole> aClass,
                                                                 HashSet<Class<? extends SecurityRole>> allRoles) {
        if (allRoles.contains(aClass) || aClass == SecurityRole.class) {
            return allRoles;
        }
        allRoles.add(aClass);
        for (Class<?> aClass1 : aClass.getInterfaces()) {
            if (SecurityRole.class.isAssignableFrom(aClass1)) {
                //noinspection unchecked
                resolveAll((Class<? extends SecurityRole>) aClass1, allRoles);
            }
        }
        return allRoles;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Converter ---------------------

    @Override
    public SecurityRolePresentation convert(Class<? extends SecurityRole> from) {
        return new SecurityRolePresentation(from);
    }
}
