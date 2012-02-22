package de.atns.common.lock;

import de.atns.common.dao.LongIdObject;

public interface LockState<TYPE extends LongIdObject> {

    LockObject<TYPE> getLockObject();

    LockPrincipal getLockPrincipal();

    boolean isTimeout();

    void refresh();
}
