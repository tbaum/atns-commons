package de.atns.common.lock;

import de.atns.common.dao.LongIdObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service("lockManager")
public class LockManagerImpl<TYPE extends LongIdObject> implements LockManager<TYPE> {

    private static final Log LOG = LogFactory.getLog(LockManagerImpl.class);

    private boolean running = true;

    private final Thread lockClear = new Thread() {
        @Override public void run() {
            while (running) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    running = false;
                }

                synchronized (locks) {
                    final Collection<LockState> toRemove = new ArrayList<LockState>(locks.size());
                    for (final LockState lockState : locks.values()) {
                        //  LockObject<TYPE> lockObject = locks.get(lockState);
                        if (lockState.isTimeout()) {
                            toRemove.add(lockState);
                            if (LOG.isDebugEnabled()) {
                                LOG.debug(MessageFormat
                                        .format("removing Lock {0}{1}", lockState, lockState.getLockObject()));
                            }
                        }
                    }
                    for (final LockState rm : toRemove) {
                        locks.remove(rm);
                    }
                }
            }
        }
    };

    private final Map<LockState<TYPE>, LockState<TYPE>> locks = new HashMap<LockState<TYPE>, LockState<TYPE>>(100);

    private long waitTime = 5000;
    private long timeoutTime = 15000;

    public LockManagerImpl() {
        lockClear.setDaemon(true);
        lockClear.start();
    }

    @Override public LockState createLock(final TYPE objectx) {
        synchronized (locks) {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
                throw new InsufficientAuthenticationException("could not retrieve authentication object");
            }

            final Object principal = auth.getPrincipal();
            if (principal == null || !(principal instanceof LockPrincipal)) {
                throw new InsufficientAuthenticationException("could not retrieve authentication object");
            }
            final LockPrincipal lockPrincipal = (LockPrincipal) principal;
            final LockObject<TYPE> lockObject = new LockObject<TYPE>(objectx);

            for (final LockState lockState : locks.values()) {
                if (lockState.getLockObject().equals(lockObject)) {
                    if (lockState.getLockPrincipal().equals(lockPrincipal)) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("object locked by myself");
                        }

                        lockState.refresh();
                        return lockState;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(MessageFormat.format("object locked by user {0}", lockState));
                    }
                    throw new LockException(objectx, lockState);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("creating new LockState for " + lockObject);
            }
            final LockStateImpl<TYPE> lockState = new LockStateImpl<TYPE>(lockPrincipal, timeoutTime, lockObject);
            locks.put(lockState, lockState);
            lockState.refresh();
            return lockState;
        }
    }

    @Override public void freeLock(final LockState object) {
        synchronized (locks) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("freeLock " + object);
            }
            locks.remove(object);
        }
    }

    @Override public LockState updateLock(final LockState object) {
        synchronized (locks) {
            final LockState lock = locks.get(object);

            if (lock == null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("updating " + object + " failed");
                }
            } else {
                lock.refresh();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("updating " + object + " ok");
                }
            }
            return lock;
        }
    }

    public void setWaitTime(final long waitTime) {
        this.waitTime = waitTime;
        this.timeoutTime = waitTime * 3;
    }
}

