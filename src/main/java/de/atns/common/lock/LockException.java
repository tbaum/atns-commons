package de.atns.common.lock;

public class LockException extends RuntimeException {

    private final LockState lock;
    private final Object object;

    public LockException(final Object object, final LockState lock) {
        super("object " + object + " is locked by " + lock.getLockPrincipal());
        this.object = object;
        this.lock = lock;
    }

    public LockState getLock() {
        return lock;
    }

    public Object getObject() {
        return object;
    }
}
