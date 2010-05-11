package de.atns.common.lock;

import de.atns.common.dao.LongIdObject;

public interface LockState<TYPE extends LongIdObject> {
    void refresh();

    LockObject<TYPE> getLockObject();

    LockPrincipal getLockPrincipal();

    boolean isTimeout();
}
