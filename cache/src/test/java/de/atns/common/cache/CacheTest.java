package de.atns.common.cache;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * @author tbaum
 * @since 13.02.2010
 */
public class CacheTest {
// ------------------------------ FIELDS ------------------------------

    private static final Object
            KEY_1 = 1, ENTRY_1 = "test 1",
            KEY_2 = 2, ENTRY_2 = "test 2",
            KEY_3 = 3, ENTRY_3 = "test 3";

    private long time;
    private TimeoutCache<Object, Object> cache;

// -------------------------- OTHER METHODS --------------------------

    @Before
    public void setUp() {
        cache = new TimeoutCache<Object, Object>(4) {
            @Override protected long getTime() {
                return time;
            }
        };

        time = 0;
        cache.put(KEY_1, ENTRY_1);
        time++;
        cache.put(KEY_2, ENTRY_2);
        time++;
        cache.put(KEY_3, ENTRY_3);
    }

    @Test
    public void testCacheRemoval() {
        Assert.assertEquals(ENTRY_1, cache.get(KEY_1));
        Assert.assertEquals(ENTRY_2, cache.get(KEY_2));
        Assert.assertEquals(ENTRY_3, cache.get(KEY_3));

        time++;
        Assert.assertEquals(ENTRY_1, cache.get(KEY_1));
        Assert.assertEquals(ENTRY_2, cache.get(KEY_2));
        Assert.assertEquals(ENTRY_3, cache.get(KEY_3));

        time++;
        assertNull(cache.get(KEY_1));
        Assert.assertEquals(ENTRY_2, cache.get(KEY_2));
        Assert.assertEquals(ENTRY_3, cache.get(KEY_3));

        time++;
        assertNull(cache.get(KEY_1));
        assertNull(cache.get(KEY_2));
        Assert.assertEquals(ENTRY_3, cache.get(KEY_3));

        time++;
        assertNull(cache.get(KEY_1));
        assertNull(cache.get(KEY_2));
        assertNull(cache.get(KEY_3));
    }
}
