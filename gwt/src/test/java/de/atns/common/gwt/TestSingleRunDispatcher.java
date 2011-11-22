package de.atns.common.gwt;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.atns.common.gwt.client.async.Callback;
import de.atns.common.gwt.client.async.SingleRunDispatcher;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.general.StringResult;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

/**
 * @author tbaum
 * @since 22.11.11
 */
public class TestSingleRunDispatcher {
// ------------------------------ FIELDS ------------------------------

    private final LinkedList<Call> jobs = new LinkedList<Call>();
    private final LinkedList<String> results = new LinkedList<String>();
    private SingleRunDispatcher dispatcher = new SingleRunDispatcher(new DispatchAsync() {
        @Override public <A extends Action<R>, R extends Result> void execute(A action, AsyncCallback<R> callback) {
            //noinspection unchecked
            jobs.add(new Call((TestAction) action, (AsyncCallback<StringResult>) callback));
        }
    }, new SimpleEventBus());

// -------------------------- OTHER METHODS --------------------------

    @Before public void setup() {
        jobs.clear();
        results.clear();
    }

    @Test public void testExecuteOnceOrRunAfterOnce() {
        dispatcher.executeOnceOrRunAfterOnce(new TestAction("a1"), new StringResultCallback(results, "c1"));
        dispatcher.executeOnceOrRunAfterOnce(new TestAction("a2"), new StringResultCallback(results, "c2"));
        dispatcher.executeOnceOrRunAfterOnce(new TestAction("a3"), new StringResultCallback(results, "c3"));
        dispatcher.executeOnceOrRunAfterOnce(new TestAction("a4"), new StringResultCallback(results, "c4"));
        dispatcher.executeOnceOrRunAfterOnce(new TestAction("a5"), new StringResultCallback(results, "c5"));
        executeJobs(jobs, results, "e1");
        dispatcher.executeOnceOrRunAfterOnce(new TestAction("a6"), new StringResultCallback(results, "c6"));
        dispatcher.executeOnceOrRunAfterOnce(new TestAction("a7"), new StringResultCallback(results, "c7"));

        executeJobs(jobs, results, "e2");

        assertEquals(asList(
                "e1",
                "a1", "c1>a1",
                "a5", "c2>a5", "c3>a5", "c4>a5", "c5>a5",
                "e2",
                "a6", "c6>a6",
                "a7", "c7>a7"),
                results);
    }

    private void executeJobs(LinkedList<Call> jobs, List<String> results, String step) {
        results.add(step);
        while (!jobs.isEmpty()) {
            Call job = jobs.removeFirst();
            results.add(job.action.name);
            job.callback.onSuccess(new StringResult(job.action.name));
        }
    }

    @Test public void testExecuteOrRunAfter() {
        dispatcher.executeOrRunAfter(new TestAction("a1"), new StringResultCallback(results, "c1"));
        dispatcher.executeOrRunAfter(new TestAction("a2"), new StringResultCallback(results, "c2"));
        dispatcher.executeOrRunAfter(new TestAction("a3"), new StringResultCallback(results, "c3"));
        dispatcher.executeOrRunAfter(new TestAction("a4"), new StringResultCallback(results, "c4"));
        dispatcher.executeOrRunAfter(new TestAction("a5"), new StringResultCallback(results, "c5"));
        executeJobs(jobs, results, "e1");
        dispatcher.executeOrRunAfter(new TestAction("a6"), new StringResultCallback(results, "c6"));
        dispatcher.executeOrRunAfter(new TestAction("a7"), new StringResultCallback(results, "c7"));

        executeJobs(jobs, results, "e2");

        assertEquals(asList(
                "e1",
                "a1", "c1>a1",
                "a2", "c2>a2",
                "a3", "c3>a3",
                "a4", "c4>a4",
                "a5", "c5>a5",
                "e2",
                "a6", "c6>a6",
                "a7", "c7>a7"),
                results);
    }

    @Test public void testOxecuteOnce() {
        dispatcher.executeOnce(new TestAction("a1"), new StringResultCallback(results, "c1"));
        dispatcher.executeOnce(new TestAction("a2"), new StringResultCallback(results, "c2"));
        dispatcher.executeOnce(new TestAction("a3"), new StringResultCallback(results, "c3"));
        dispatcher.executeOnce(new TestAction("a4"), new StringResultCallback(results, "c4"));
        dispatcher.executeOnce(new TestAction("a5"), new StringResultCallback(results, "c5"));
        executeJobs(jobs, results, "e1");
        dispatcher.executeOnce(new TestAction("a6"), new StringResultCallback(results, "c6"));
        dispatcher.executeOnce(new TestAction("a7"), new StringResultCallback(results, "c7"));

        executeJobs(jobs, results, "e2");

        assertEquals(asList(
                "e1",
                "a1", "c1>a1", "c2>a1", "c3>a1", "c4>a1", "c5>a1",
                "e2",
                "a6", "c6>a6", "c7>a6"),
                results);
    }

// -------------------------- INNER CLASSES --------------------------

    private static class StringResultCallback extends Callback<StringResult> {
        private final List<String> results;
        private final String name;

        public StringResultCallback(List<String> results, String name) {
            this.results = results;
            this.name = name;
        }

        @Override public void onSuccess(StringResult result) {
            results.add(name + ">" + result.get());
        }
    }

    private class Call {
        private final TestAction action;
        private final AsyncCallback<StringResult> callback;

        public Call(TestAction action, AsyncCallback<StringResult> callback) {
            this.action = action;
            this.callback = callback;
        }
    }

    private class TestAction implements Action {
        private final String name;

        public TestAction(String name) {
            this.name = name;
        }
    }
}
