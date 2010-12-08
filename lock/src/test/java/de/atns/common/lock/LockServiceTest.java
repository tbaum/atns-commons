package de.atns.common.lock;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Thomas Baum
 * @since 08.12.2010
 */
public class LockServiceTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void testDifferent() throws Exception {

        final TestService testService = new TestService();
        Thread[] tr = new Thread[10];

        for (int i = 0; i < tr.length; i++) {
            tr[i] = new Thread(new TestRunner(testService, "id" + i));
        }

        long start = System.currentTimeMillis();
        for (Thread aTr : tr) {
            aTr.start();
        }

        for (Thread aTr : tr) {
            aTr.join(5000);
        }

        for (Thread aTr : tr) {
            assertFalse(aTr.isAlive());
        }

        assertTrue(System.currentTimeMillis() - start < 500);
    }

    @Test
    public void testSame() throws Exception {
        final TestService testService = new TestService();
        Thread[] tr = new Thread[10];

        for (int i = 0; i < tr.length; i++) {
            tr[i] = new Thread(new TestRunner(testService, "id1"));
        }

        long start = System.currentTimeMillis();
        for (Thread aTr : tr) {
            aTr.start();
        }

        for (Thread aTr : tr) {
            aTr.join(5000);
        }

        for (Thread aTr : tr) {
            assertFalse(aTr.isAlive());
        }
        assertTrue(System.currentTimeMillis() - start > 500);

    }

// -------------------------- INNER CLASSES --------------------------

    private static class TestRunner implements Runnable {
        private final TestService testService;
        private final String id;

        public TestRunner(TestService testService, final String id) {
            this.testService = testService;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                testService.doTest(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class TestService {
        private final LockService<String> lockService = new LockService<String>();
        private final Set<String> running = Collections.synchronizedSet(new HashSet<String>());

        public void doTest(String id) throws InterruptedException {
            lockService.lock(id);
            try {
                assertFalse(running.contains(id));
                running.add(id);
                Thread.sleep(200);
                running.remove(id);
            } finally {
                lockService.unlock(id);
            }
        }
    }
}
