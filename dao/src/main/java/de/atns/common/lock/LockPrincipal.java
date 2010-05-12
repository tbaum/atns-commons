package de.atns.common.lock;

import java.security.Principal;


public interface LockPrincipal extends Principal {
// -------------------------- OTHER METHODS --------------------------

    long getId();
}
