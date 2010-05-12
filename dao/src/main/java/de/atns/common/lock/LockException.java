package de.atns.common.lock;

public class LockException extends RuntimeException {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -2833246476651470549L;
    private final LockState lock;
    private final Object object;

// --------------------------- CONSTRUCTORS ---------------------------

    public LockException(final Object object, final LockState lock) {
        super("object " + object + " is locked by " + lock.getLockPrincipal());
        this.object = object;
        this.lock = lock;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public LockState getLock() {
        return lock;
    }

    public Object getObject() {
        return object;
    }
}
