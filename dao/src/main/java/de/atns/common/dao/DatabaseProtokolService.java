package de.atns.common.dao;

import com.google.inject.Inject;
import com.google.inject.OutOfScopeException;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;
import de.atns.common.filter.AnnotatedFieldFilter;
import de.atns.common.filter.DeclaringClassFieldFilter;
import de.atns.common.filter.Filter;
import de.atns.common.security.SecurityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Version;
import java.lang.reflect.Field;
import java.util.Date;

import static de.atns.common.dao.LogEntry.Action.*;

/**
 * @author tbaum
 * @since 09.07.11
 */
@Singleton
public class DatabaseProtokolService {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(DatabaseProtokolService.class);

    private static DatabaseProtokolService instance;

    private String myPackage = "";

    private Filter<Field> ignoreFilter = new Filter<Field>() {
        @Override public boolean isInFilter(Field o) {
            return Iterable.class.isAssignableFrom(o.getType());
        }
    };

    private final Provider<EntityManager> em;
    private final SecurityService securityService;

// -------------------------- STATIC METHODS --------------------------

    public static void logPersist(Object object) {
        instance.writeLogEntry(object, persist);
    }

    @Transactional protected void writeLogEntry(Object object, LogEntry.Action action) {
        try {
            String daten = dumpClass(object, object.getClass(), ", ", true, ignoreFilter).toString();
            String login = getCurrentUser();
            em.get().persist(new LogEntry(login, action,
                    simplifyName(object.getClass()),
                    dumpAnnotatedFields(object, new AnnotatedFieldFilter(Id.class)),
                    dumpAnnotatedFields(object, new AnnotatedFieldFilter(Version.class)),
                    daten));
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

    private String getCurrentUser() {
        String login = null;
        try {
            login = securityService.currentUser().getLogin();
        } catch (OutOfScopeException ignored) {
        }
        return login;
    }

    private String simplifyName(Class aClass) {
        String name = aClass.getName();
        return name.startsWith(myPackage) ? name.substring(myPackage.length()) : name;
    }

    private String dumpAnnotatedFields(Object object, AnnotatedFieldFilter filter) {
        return dumpClass(object, object.getClass(), ".", false, filter).toString();
    }

    public static void logRemove(Object object) {
        instance.writeLogEntry(object, remove);
    }

    public static void logUpdate(Object object) {
        instance.writeLogEntry(object, update);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public DatabaseProtokolService(Provider<EntityManager> em, SecurityService securityService) {
        this.em = em;
        this.securityService = securityService;
        if (instance != null) {
            LOG.warn("instance already set!");
        }
        instance = this;
    }

// -------------------------- OTHER METHODS --------------------------

    private StringBuilder dumpClass(Object object, Class<?> aClass, String delimiter, boolean showFieldName,
                                    Filter<Field> filter) {
        final StringBuilder builder = new StringBuilder();

        final Field[] fields = aClass.getDeclaredFields();

        for (Field field : fields) {
            if (filter.isInFilter(field)) {
                continue;
            }

            addDelimiter(builder, delimiter);

            if (showFieldName) {
                builder.append(field.getName()).append("=");
            }
            builder.append(getValue(object, field));
        }

        if (aClass != Object.class) {
            StringBuilder stringBuilder = dumpClass(object, aClass.getSuperclass(), delimiter, showFieldName, filter);
            if (stringBuilder.length() > 0) {
                addDelimiter(builder, delimiter);
                builder.append(stringBuilder);
            }
        }

        return builder;
    }

    private String getValue(Object object, Field field) {
        String result;
        try {
            field.setAccessible(true);
            result = dumpValue(field.get(object));
        } catch (IllegalAccessException e) {
            result = "Error:[" + (object != null ? simplifyName(object.getClass()) : "null") + "] " + e.getMessage();
        }
        return result;
    }

    private String dumpValue(Object value) throws IllegalAccessException {
        if (value == null) {
            return "null";
        }

        final Class<?> valueClass = value.getClass();
        if (valueClass.isPrimitive() || value instanceof Number) {
            return String.valueOf(value);
        }

        if (value instanceof String || value instanceof Date || value instanceof Enum) {
            return "'" + String.valueOf(value).replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "\\\\'") + "'";
        }

        if (value instanceof Iterable) {
            return dumpIterable((Iterable) value);
        }

        if (valueClass.getAnnotation(Entity.class) != null) {
            return String.format("%s(%s#%s)", simplifyName(valueClass),
                    dumpAnnotatedFields(value, new AnnotatedFieldFilter(Id.class)),
                    dumpAnnotatedFields(value, new AnnotatedFieldFilter(Version.class)));
        }

        throw new IllegalAccessException("Unbekannte Klasse " + valueClass);
    }

    private String dumpIterable(Iterable s) throws IllegalAccessException {
        final StringBuilder builder = new StringBuilder();
        for (Object o : s) {
            addDelimiter(builder, ", ");
            builder.append(dumpValue(o));
        }
        return "[" + builder.toString() + "]";
    }

    private void addDelimiter(StringBuilder builder, String delimiter) {
        if (builder.length() != 0) {
            builder.append(delimiter);
        }
    }

    @Inject(optional = true)
    public void setIgnoreFilter(@Named("ignoreLogClass") Class ignoreClass) {
        this.ignoreFilter = new DeclaringClassFieldFilter(ignoreClass);
    }

    @Inject(optional = true)
    public void setMyPackage(@Named("shortLogPackageName") Package myPackage) {
        this.myPackage = myPackage.getName();
    }
}
