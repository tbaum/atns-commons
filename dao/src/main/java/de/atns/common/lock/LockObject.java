package de.atns.common.lock;

import de.atns.common.dao.LongIdObject;

import java.io.Serializable;
import java.text.MessageFormat;

public class LockObject<TYPE extends LongIdObject> implements Serializable {

    private final Long id;
    private final Class<? extends LongIdObject> clazz;
    private final int hashCode;

    public LockObject(final TYPE object) {
        this.id = object.getId();
        clazz = object.getClass();
        hashCode = object.hashCode();
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final LockObject that = (LockObject) o;

        if (hashCode != that.hashCode) {
            return false;
        }
        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
    }

    @Override public int hashCode() {
        return (int) (id ^ (id >>> 32) + (clazz.hashCode()));
    }

    @Override public String toString() {
        return MessageFormat.format("LockObject {1}@{2}", clazz, id);
    }
}
