package de.atns.common.lock;

public interface LockManager<TYPE> {

    LockState createLock(TYPE object);

    void freeLock(LockState object);

    LockState updateLock(LockState object);
}
