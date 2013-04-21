package de.atns.common.dao;

import ch.lambdaj.function.convert.Converter;
import de.atns.common.exception.DeletionFailedException;
import de.atns.common.lock.LockManager;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public interface GenericManager<TYPE extends LongIdObject> extends LockManager<TYPE>, Converter<Long, TYPE> {

    TYPE createObject();

    void deleteObject(TYPE object) throws DeletionFailedException;

    Collection<TYPE> loadAll();

    Collection<TYPE> loadAll(Date lastModifiedTimestamp);

    PartResult<TYPE> loadAll(Map<String, Object> filter, int start, int max, String sort, boolean asc);

    TYPE loadReadonly(long id);

    TYPE loadReadonly(long id, String... detachedFields);

    TYPE loadWriteable(final long id);

    TYPE merge(TYPE object);
}
