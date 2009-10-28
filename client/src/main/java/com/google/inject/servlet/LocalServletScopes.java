package com.google.inject.servlet;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import static com.google.inject.servlet.ServletScopes.REQUEST;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tbaum
 * @since 13.10.2009 11:12:24
 */
public class LocalServletScopes {
// ------------------------------ FIELDS ------------------------------

    public static final Scope LOCAL_REQUEST = new Scope() {
        public <T> Provider<T> scope(final Key<T> key, final Provider<T> creator) {
            return new Provider<T>() {
                public T get() {
                    final HttpServletRequest request = GuiceFilter.getRequest();
                    synchronized (request) {
                        final String remoteHost = request.getRemoteHost();
                        if (!remoteHost.startsWith("0:0:0:0:0:0:0:1") && !remoteHost.equals("127.0.0.1")) {
                            throw new RuntimeException("remote-access not permitted");
                        }
                        return REQUEST.scope(key, creator).get();
                    }
                }

                public String toString() {
                    return String.format("%s[%s]", creator, LOCAL_REQUEST);
                }
            };
        }

        public String toString() {
            return "ServletScopes.REQUEST";
        }
    };

// --------------------------- CONSTRUCTORS ---------------------------

    private LocalServletScopes() {
    }
}
