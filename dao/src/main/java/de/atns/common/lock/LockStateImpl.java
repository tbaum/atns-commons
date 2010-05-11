package de.atns.common.lock;

import de.atns.common.dao.LongIdObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.text.MessageFormat;

public class LockStateImpl<TYPE extends LongIdObject> implements Serializable, LockState<TYPE> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(LockStateImpl.class);
    private static final long serialVersionUID = 6078902170038408948L;
    private final long timeoutTime;
    private final LockObject<TYPE> lockObject;
    private long lastSeen;
    private final LockPrincipal lockPrincipal;
    private final long id;

// --------------------------- CONSTRUCTORS ---------------------------

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

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override public LockObject<TYPE> getLockObject() {
        return lockObject;
    }

    @Override public LockPrincipal getLockPrincipal() {
        return lockPrincipal;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(final Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && id == ((LockStateImpl) o).id;
    }

    @Override
    public String toString() {
        return MessageFormat.format("LockState@{0} {1}/{2}", Integer.toHexString(hashCode()),
                Long.toHexString(lockPrincipal.getId()), lockPrincipal.getName());
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface LockState ---------------------

    @Override public boolean isTimeout() {
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("isTimeout " + (System.currentTimeMillis() - lastSeen) + " > " + timeoutTime);
//        }
        return System.currentTimeMillis() - lastSeen > timeoutTime;
    }
}
