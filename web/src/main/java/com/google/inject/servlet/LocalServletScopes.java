package com.google.inject.servlet;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tbaum
 * @since 13.10.2009 11:12:24
 */
public class LocalServletScopes {
// ------------------------------ FIELDS ------------------------------

    public static final Scope LOCAL_REQUEST = new Scope() {
        @Override public <T> Provider<T> scope(final Key<T> key, final Provider<T> creator) {
            return new Provider<T>() {
                @Override public T get() {
                    final HttpServletRequest request = GuiceFilter.getRequest();
                    //noinspection SynchronizationOnLocalVariableOrMethodParameter
                    synchronized (request) {
                        final String remoteHost = request.getRemoteHost();
                        if (!remoteHost.startsWith("0:0:0:0:0:0:0:1") && !remoteHost.equals("127.0.0.1")) {
                            throw new RuntimeException("remote-access not permitted");
                        }
                        return ServletScopes.REQUEST.scope(key, creator).get();
                    }
                }

                @Override public String toString() {
                    return String.format("%s[%s]", creator, LOCAL_REQUEST);
                }
            };
        }

        @Override public String toString() {
            return "ServletScopes.REQUEST";
        }
    };

// --------------------------- CONSTRUCTORS ---------------------------

    private LocalServletScopes() {
    }
}
