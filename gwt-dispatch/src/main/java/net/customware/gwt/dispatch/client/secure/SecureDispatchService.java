package net.customware.gwt.dispatch.client.secure;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

@RemoteServiceRelativePath("dispatch")
public interface SecureDispatchService extends RemoteService {
    Result execute(String sessionId, Action<?> action) throws DispatchException;
}
