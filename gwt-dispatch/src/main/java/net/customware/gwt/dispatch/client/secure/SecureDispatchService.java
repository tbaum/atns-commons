package net.customware.gwt.dispatch.client.secure;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.ServiceException;

@RemoteServiceRelativePath("dispatch")
public interface SecureDispatchService extends RemoteService {
// -------------------------- OTHER METHODS --------------------------

    <R extends Result> R execute(String sessionId, Action<R> action) throws ActionException, ServiceException;
}
