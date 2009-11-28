package de.atns.common.security;

import java.lang.reflect.Method;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class NotInRoleException extends SecurityException {
// --------------------------- CONSTRUCTORS ---------------------------

    public NotInRoleException(final Method method) {
        super("invalid role to access " + method.toString());
    }
}
