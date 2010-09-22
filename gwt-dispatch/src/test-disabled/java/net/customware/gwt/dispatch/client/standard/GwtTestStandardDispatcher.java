package net.customware.gwt.dispatch.client.standard;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import net.customware.gwt.dispatch.client.ExceptionHandler;
import net.customware.gwt.dispatch.shared.BatchAction;
import net.customware.gwt.dispatch.shared.BatchAction.OnException;
import net.customware.gwt.dispatch.shared.BatchResult;
import net.customware.gwt.dispatch.shared.counter.IncrementCounter;
import net.customware.gwt.dispatch.shared.counter.IncrementCounterResult;
import net.customware.gwt.dispatch.shared.counter.ResetCounter;
import net.customware.gwt.dispatch.shared.counter.ResetCounterResult;

public class GwtTestStandardDispatcher extends GWTTestCase {

    private abstract static class TestCallback<T> implements AsyncCallback<T> {
        public void onFailure(Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static final int TEST_DELAY = 500;

    private StandardDispatchAsync dispatch;

    @Override
    public String getModuleName() {
        return "net.customware.gwt.dispatch.DispatchTest";
    }

    @Override
    protected void gwtSetUp() throws Exception {

        dispatch = new StandardDispatchAsync(new ExceptionHandler() {
            public Status onFailure(Throwable e) {
                throw new RuntimeException(e);
            }
        });

        super.gwtSetUp();
    }

    @Override
    protected void gwtTearDown() throws Exception {
        dispatch = null;

        super.gwtTearDown();
    }

    public void testIncrementCounter() {

        dispatch.execute(new IncrementCounter(1), new TestCallback<IncrementCounterResult>() {
            public void onSuccess(IncrementCounterResult result) {
                assertEquals(1, result.getCurrent());
                finishTest();
            }
        });

        // Set a delay period significantly longer than the
        // event is expected to take.
        delayTestFinish(TEST_DELAY);
    }

    public void testBatchAction() {

        BatchAction batch = new BatchAction(OnException.ROLLBACK,
                new ResetCounter(), new IncrementCounter(1), new IncrementCounter(2));

        dispatch.execute(batch, new TestCallback<BatchResult>() {
            public void onSuccess(BatchResult result) {
                assertEquals(3, result.size());

                assertTrue(result.getResult(0) instanceof ResetCounterResult);
                assertEquals(0, result.getResult(0, ResetCounterResult.class).getNewValue());

                assertTrue(result.getResult(1) instanceof IncrementCounterResult);
                assertEquals(1, result.getResult(1, IncrementCounterResult.class).getAmount());
                assertEquals(1, result.getResult(1, IncrementCounterResult.class).getCurrent());

                assertTrue(result.getResult(2) instanceof IncrementCounterResult);
                assertEquals(2, result.getResult(2, IncrementCounterResult.class).getAmount());
                assertEquals(3, result.getResult(2, IncrementCounterResult.class).getCurrent());

                finishTest();
            }
        });

        // Set a delay period significantly longer than the
        // event is expected to take.
        delayTestFinish(TEST_DELAY);
    }

    public void testIncrementCounterSequence() {

        dispatch.execute(new ResetCounter(), new TestCallback<ResetCounterResult>() {
            public void onSuccess(ResetCounterResult result) {
                dispatch.execute(new IncrementCounter(1), new TestCallback<IncrementCounterResult>() {
                    public void onSuccess(IncrementCounterResult result) {
                        assertEquals(1, result.getCurrent());

                        dispatch.execute(new IncrementCounter(2), new AsyncCallback<IncrementCounterResult>() {
                            public void onFailure(Throwable caught) {
                                throw new RuntimeException(caught);
                            }

                            public void onSuccess(IncrementCounterResult result) {
                                assertEquals(3, result.getCurrent());
                                finishTest();
                            }
                        });
                    }
                });
            }
        });

        // Set a delay period significantly longer than the
        // event is expected to take.
        delayTestFinish(TEST_DELAY);
    }

}
