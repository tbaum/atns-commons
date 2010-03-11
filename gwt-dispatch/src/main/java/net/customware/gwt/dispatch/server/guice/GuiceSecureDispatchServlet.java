package net.customware.gwt.dispatch.server.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.customware.gwt.dispatch.client.secure.SecureDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.secure.AbstractSecureDispatchServlet;
import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;
import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;

/**
 * A servlet implementation of the {@link SecureDispatchService}. This will
 * call the provided {@link SecureSessionValidator} to confirm that the provided
 * session ID is still valid before executing any actions. If not, an
 * {@link InvalidSessionException} is thrown back to the client.
 *
 * @author David Peterson
 */
@Singleton
public class GuiceSecureDispatchServlet extends AbstractSecureDispatchServlet implements SecureDispatchService {
// ------------------------------ FIELDS ------------------------------

    private final Dispatch dispatch;

    private final SecureSessionValidator sessionValidator;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public GuiceSecureDispatchServlet(Dispatch dispatch, SecureSessionValidator sessionValidator) {
        this.dispatch = dispatch;
        this.sessionValidator = sessionValidator;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override
    protected Dispatch getDispatch() {
        return dispatch;
    }

    @Override
    public SecureSessionValidator getSessionValidator() {
        return sessionValidator;
    }
}
