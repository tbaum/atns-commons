package de.atns.common.lock;

import de.atns.common.dao.LongIdObject;

import java.io.Serializable;
import java.text.MessageFormat;

public class LockStateImpl<TYPE extends LongIdObject> implements Serializable, LockState<TYPE> {

    private final long timeoutTime;
    private final LockObject<TYPE> lockObject;
    private long lastSeen;
    private final LockPrincipal lockPrincipal;
    private final long id;

    public LockStateImpl(final LockPrincipal lockPrincipal, final long timeoutTime, final LockObject<TYPE> lockObject) {
        assert lockPrincipal != null;
        assert lockObject != null;
        this.lockPrincipal = lockPrincipal;
        this.timeoutTime = timeoutTime;
        this.lockObject = lockObject;
        id = System.currentTimeMillis() + System.nanoTime();
        refresh();
    }

    @Override public void refresh() {
        this.lastSeen = System.currentTimeMillis();
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("refresh " + this.lastSeen);
//        }
    }

    @Override public LockObject<TYPE> getLockObject() {
        return lockObject;
    }

    @Override public LockPrincipal getLockPrincipal() {
        return lockPrincipal;
    }

    @Override public boolean equals(final Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && id == ((LockStateImpl) o).id;
    }

    @Override public String toString() {
        return MessageFormat.format("LockState@{0} {1}/{2}", Integer.toHexString(hashCode()),
                Long.toHexString(lockPrincipal.getId()), lockPrincipal.getName());
    }

    @Override public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


    @Override public boolean isTimeout() {
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("isTimeout " + (System.currentTimeMillis() - lastSeen) + " > " + timeoutTime);
//        }
        return System.currentTimeMillis() - lastSeen > timeoutTime;
    }
}
