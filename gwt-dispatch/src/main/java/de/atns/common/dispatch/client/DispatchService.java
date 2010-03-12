package de.atns.common.dispatch.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("dispatch")
public interface DispatchService extends RemoteService {
// -------------------------- OTHER METHODS --------------------------

    Result execute(Action<Result> action) throws ActionException, ServiceException;
}