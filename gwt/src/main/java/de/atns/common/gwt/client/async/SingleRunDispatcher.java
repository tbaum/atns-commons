package de.atns.common.gwt.client.async;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tbaum
 * @since 19.11.11
 */
@Singleton public class SingleRunDispatcher {
// ------------------------------ FIELDS ------------------------------

    private final DispatchAsync dispatcher;
    private final EventBus eventBus;
    private final Map<Class<? extends Action>, QueingCallback<? extends Result>> running =
            new HashMap<Class<? extends Action>, QueingCallback<? extends Result>>();

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public SingleRunDispatcher(DispatchAsync dispatcher, EventBus eventBus) {
        this.dispatcher = dispatcher;
        this.eventBus = eventBus;
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Läuft bereits eine Action der selben Klasse wird nach Abschluss die übergebene Action ausgeführt, alle dannach
     * übergeben Actions werden eingereiht und nicht mehrfach ausgeführt
     * <p/>
     * bsp: Aufrufe
     * <li>1. exec: Action1,Callback1
     * <li>2. exec: Action2,Callback2
     * <li>3. exec: Action3,Callback3
     * <li>4. (Action1-beendet)... Callback1 mit dem Ergebnis von Action1
     * <li>5. (Action2-beendet)... Callback2, Callback3 mit dem Ergebnis von Action2
     * <li>6. (Action3 wird nicht ausgeführt)
     * <li>7. exec: Action4,Callback4
     * <li>8. exec: Action5,Callback5
     * <li>9. (Action4-beendet).. Callback4 mit dem Ergebnis von Action4
     * <li>10. (Action5-beendet).. Callback5 mit dem Ergebnis von Action5
     */
    public <A extends Action<R>, R extends Result> void executeOnceOrRunAfterOnce(final A action, final AsyncCallback<R> callback) {
        final Class<? extends Action> clazz = action.getClass();

        if (isRunning(clazz)) {
            QueingCallback<R> queingCallback = getCallback(clazz);

            RetriggerCallback<A, R> asyncCallback = findRetriggerCallback(queingCallback);
            if (asyncCallback == null) {
                queingCallback.add(new RetriggerCallback<A, R>(action, callback));
            } else {
                asyncCallback.setAction(action);
                asyncCallback.addCallback(callback);
            }
        } else {
            executeOnce(action, callback);
        }
    }

    private boolean isRunning(Class<? extends Action> clazz) {
        return running.containsKey(clazz);
    }

    private <R extends Result> QueingCallback<R> getCallback(Class<? extends Action> clazz) {
        //noinspection unchecked
        return (QueingCallback<R>) running.get(clazz);
    }

    private <A extends Action<R>, R extends Result> RetriggerCallback<A, R> findRetriggerCallback(QueingCallback<R> queingCallback) {
        for (AsyncCallback<R> rAsyncCallback : queingCallback) {
            if (rAsyncCallback instanceof RetriggerCallback) {
                return (RetriggerCallback<A, R>) rAsyncCallback;
            }
        }
        return null;
    }

    /**
     * Action nur ausgeführt wenn noch keine Action der selben Klasse ausgeführt wird. Läuft bereits eine Action wird
     * der Callback nach Abschluss der ersten Action mit aufgerufen (d.h. Action wird nicht mehrfach ausgeführt)
     * <p/>
     * bsp: Aufrufe
     * <li>1. exec: Action1,Callback1
     * <li>2. exec: Action2,Callback2
     * <li>3. exec: Action3,Callback3
     * <li>4. (Action1-beendet)... Callback1, Callback2, Callback3 mit dem Ergebnis von Action1
     * <li>5. (Action2 wird nicht ausgeführt)
     * <li>6. (Action3 wird nicht ausgeführt)
     * <li>7. exec: Action4,Callback4
     * <li>8. exec: Action5,Callback5
     * <li>9. (Action4-beendet).. Callback4, Callback5 mit dem Ergebnis von Action4
     * <li>10
     * . (Action5 wird nicht ausgeführt)
     */
    public <A extends Action<R>, R extends Result> void executeOnce(A action, final AsyncCallback<R> callback) {
        final Class<? extends Action> clazz = action.getClass();
        if (isRunning(clazz)) {
            QueingCallback<R> queingCallback = getCallback(clazz);
            queingCallback.add(callback);
        } else {
            //noinspection unchecked
            QueingCallback<R> queingCallback = new QueingCallback<R>(new FinishedCallback<R>(this, clazz), callback);
            startRequest(clazz, queingCallback);

            dispatcher.execute(action, queingCallback);
        }
    }

    private <A extends Action<R>, R extends Result> void startRequest(Class<A> clazz, QueingCallback<R> callback1) {
        running.put(clazz, callback1);
        eventBus.fireEvent(new DispatcherStateChange(running));
    }

    /**
     * Einreihen der Actions in eine Warteschlange gleicher Action-Klasse, jede Action wird der Reihe nach abgearbeitet.
     * <p/>
     * bsp: Aufrufe
     * <li>1. exec: Action1,Callback1
     * <li>2. exec: Action2,Callback2
     * <li>3. exec: Action3,Callback3
     * <li>4. (Action1-beendet)... Callback1 mit dem Ergebnis von Action1
     * <li>4. (Action2-beendet)... Callback2 mit dem Ergebnis von Action2
     * <li>4. (Action3-beendet)... Callback3 mit dem Ergebnis von Action3
     * <li>5. exec: Action4,Callback4
     * <li>6. exec: Action5,Callback5
     * <li>7. (Action4-beendet).. Callback4 mit dem Ergebnis von Action4
     * <li>7. (Action5-beendet).. Callback5 mit dem Ergebnis von Action5
     */
    public <A extends Action<R>, R extends Result> void executeOrRunAfter(final A action, final AsyncCallback<R> callback) {
        final Class<? extends Action> clazz = action.getClass();

        if (isRunning(clazz)) {
            QueingCallback<R> queingCallback = getCallback(clazz);

            queingCallback.add(new AsyncCallback<R>() {
                @Override public void onFailure(Throwable caught) {
                    executeOrRunAfter(action, callback);
                }

                @Override public void onSuccess(R result) {
                    executeOrRunAfter(action, callback);
                }
            });
        } else {
            executeOnce(action, callback);
        }
    }

    protected void requestFinished(Class<? extends Action> clazz) {
        running.remove(clazz);
        eventBus.fireEvent(new DispatcherStateChange(running));
    }

// -------------------------- INNER CLASSES --------------------------

    private class RetriggerCallback<A extends Action<R>, R extends Result> implements AsyncCallback<R> {
        private A action;
        private final QueingCallback<R> callbacks;

        public RetriggerCallback(A action, AsyncCallback<R> callback) {
            this.action = action;

            //noinspection unchecked
            this.callbacks = new QueingCallback<R>(callback);
        }

        @Override public void onFailure(Throwable caught) {
            SingleRunDispatcher.this.executeOnce(action, callbacks);
        }

        @Override public void onSuccess(R result) {
            SingleRunDispatcher.this.executeOnce(action, callbacks);
        }

        public void setAction(A action) {
            this.action = action;
        }

        public void addCallback(AsyncCallback<R> callback) {
            this.callbacks.add(callback);
        }
    }
}
