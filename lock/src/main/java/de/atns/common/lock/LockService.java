package de.atns.common.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * @author Thomas Baum
 * @since 08.12.2010
 */
public class LockService<T> {

    private final ConcurrentHashMap<T, Semaphore> lockMap = new ConcurrentHashMap<T, Semaphore>();

    public void lock(T uuid) {
        if (uuid == null) {
            return;
        }

        Semaphore newSemaphore = new Semaphore(1, false);
        Semaphore semaphore = lockMap.putIfAbsent(uuid, newSemaphore);
        if (semaphore == null) {
            semaphore = newSemaphore;
        }

        semaphore.acquireUninterruptibly();

        lockMap.putIfAbsent(uuid, semaphore);
    }

    public void unlock(T uuid) {
        if (uuid == null) {
            return;
        }

        final Semaphore semaphore = lockMap.get(uuid);
        lockMap.remove(uuid);
        semaphore.release();
    }
}
