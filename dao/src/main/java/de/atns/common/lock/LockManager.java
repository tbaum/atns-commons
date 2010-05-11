package de.atns.common.lock;

public interface LockManager<TYPE> {
// -------------------------- OTHER METHODS --------------------------

    LockState createLock(TYPE object);

    void freeLock(LockState object);

    LockState updateLock(LockState object);
}
