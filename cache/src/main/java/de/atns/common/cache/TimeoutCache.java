package de.atns.common.cache;

import java.util.*;

/**
 * @author tbaum
 * @since 13.02.2010
 */
public class TimeoutCache<K, V> {
// ------------------------------ FIELDS ------------------------------

    private final long ttl;
    private final Map<K, V> cache = new HashMap<K, V>();
    private final Map<K, AccessTime> accessMap = new HashMap<K, AccessTime>();
    private final SortedSet<AccessTime> lastAccess = new TreeSet<AccessTime>();

// --------------------------- CONSTRUCTORS ---------------------------

    public TimeoutCache(final long ttl) {
        this.ttl = ttl;
    }

// -------------------------- OTHER METHODS --------------------------

    public V get(final K key) {
        flushCache();
        return cache.get(key);
    }

    private void flushCache() {
        synchronized (this) {
            for (Iterator<AccessTime> iterator = lastAccess.iterator(); iterator.hasNext();) {
                final AccessTime lastAcces = iterator.next();
                if (lastAcces.isTimeout()) {
                    cache.remove(lastAcces.key);
                    iterator.remove();
                } else {
                    break;
                }
            }
        }
    }

    protected long getTime() {
        return System.currentTimeMillis();
    }

    public void put(final K k, final V v) {
        synchronized (this) {
            removeAccess(k);
            cache.put(k, v);
            final AccessTime time = new AccessTime(k);
            accessMap.put(k, time);
            lastAccess.add(time);
            flushCache();
        }
    }

    private void removeAccess(final K key) {
        AccessTime t = accessMap.remove(key);
        if (t != null) {
            lastAccess.remove(t);
        }
    }

    public void removeValue(final V v) {
        synchronized (this) {
            for (Map.Entry<K, V> kvEntry : cache.entrySet()) {
                if (kvEntry.getValue().equals(v)) {
                    cache.remove(kvEntry.getKey());
                    removeAccess(kvEntry.getKey());
                }
            }
            flushCache();
        }
    }

// -------------------------- INNER CLASSES --------------------------

    private class AccessTime implements Comparable<AccessTime> {
        private final Long time;
        private final K key;


        private boolean isTimeout() {
            return getTime() - time >= ttl;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            @SuppressWarnings({"unchecked"})
            final AccessTime that = (AccessTime) o;

            return key.equals(that.key);
        }

        @Override
        public int hashCode() {
            return 31 * time.hashCode() + key.hashCode();
        }

        AccessTime(final K key) {
            this.time = getTime();
            this.key = key;
        }

        @Override public int compareTo(final AccessTime other) {
            int c = time.compareTo(other.time);
            return c == 0 ? ((Integer) hashCode()).compareTo(other.hashCode()) : c;
        }
    }
}
