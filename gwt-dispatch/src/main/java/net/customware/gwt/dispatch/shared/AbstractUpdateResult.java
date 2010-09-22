package net.customware.gwt.dispatch.shared;

/**
 * Provides support for simple update response which contain both the old value
 * and new value.
 *
 * @param <T> The value type.
 * @author David Peterson
 */
public abstract class AbstractUpdateResult<T> implements Result {
    private T oldValue;

    private T newValue;

    /**
     * For serialization support only. Subclasses should provide an
     * package-local (aka default) empty constructor.
     */
    protected AbstractUpdateResult() {
    }

    public AbstractUpdateResult(T oldValue, T newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * The previous value.
     *
     * @return The old value.
     */
    public T getOld() {
        return oldValue;
    }

    /**
     * The new/current value.
     *
     * @return The new value.
     */
    public T getNew() {
        return newValue;
    }
}
