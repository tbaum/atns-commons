package de.atns.common.dao;

import de.atns.common.filter.DeleteFlagAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * @author mwolter
 * @since 10.05.2010 11:41:54
 */
public abstract class GenericManagerNamedDeleteFlagImpl<TYPE extends LongIdObject & DeleteFlagAware & NameFieldObject>
        extends GenericManagerDeleteFlagImpl<TYPE> {
// -------------------------- OTHER METHODS --------------------------

    @Transactional(readOnly = true, propagation = REQUIRES_NEW)
    public Map<Long, String> loadNameList() {
        final Map<Long, String> result = new HashMap<Long, String>();
        for (TYPE e : loadAll()) {
            result.put(e.getId(), e.getName());
        }
        return result;
    }
}
