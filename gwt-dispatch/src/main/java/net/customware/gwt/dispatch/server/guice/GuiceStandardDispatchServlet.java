package net.customware.gwt.dispatch.server.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.standard.AbstractStandardDispatchServlet;

@Singleton
public class GuiceStandardDispatchServlet extends AbstractStandardDispatchServlet {
    private final Dispatch dispatch;

    @Inject
    public GuiceStandardDispatchServlet(Dispatch dispatch) {
        this.dispatch = dispatch;
    }

    @Override
    protected Dispatch getDispatch() {
        return dispatch;
    }
}
