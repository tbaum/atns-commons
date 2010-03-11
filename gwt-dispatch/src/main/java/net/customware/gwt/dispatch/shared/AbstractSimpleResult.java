package net.customware.gwt.dispatch.shared;

/**
 * A common use-case is returning a single value from an action. This provides a
 * simple, type-safe superclass for such results.
 * <p/>
 * <p/>
 * <b>Note:</b> Subclasses should provide both an empty constructor for serialization and a
 * constructor with a single value for normal use. It is recommended that the
 * empty constructor is private or package-private.
 *
 * @author David Peterson
 * @param <T>
 * The value type.
 */
public abstract class AbstractSimpleResult<T> implements Result {

    private T value;

    protected AbstractSimpleResult() {
    }

    public AbstractSimpleResult(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

}
