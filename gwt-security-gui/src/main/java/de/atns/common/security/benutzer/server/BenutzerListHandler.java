package de.atns.common.security.benutzer.server;

import ch.lambdaj.function.convert.Converter;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.Transactional;
import de.atns.common.dao.PartResult;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.gwt.server.ConvertingActionHandler;
import de.atns.common.gwt.server.ListConverter;
import de.atns.common.security.Secured;
import de.atns.common.security.benutzer.client.action.BenutzerList;
import de.atns.common.security.benutzer.client.model.MitarbeiterPresentation;
import de.atns.common.security.model.Benutzer;
import net.customware.gwt.dispatch.shared.ActionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.atns.common.dao.PartResult.createPartResult;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerListHandler extends ConvertingActionHandler<BenutzerList, ListPresentation<MitarbeiterPresentation>, PartResult<Benutzer>> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(BenutzerListHandler.class);
    private final Provider<EntityManager> em;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerListHandler(final Provider<EntityManager> em) {
        super(new ListConverter<Benutzer, MitarbeiterPresentation>(new Converter<Benutzer, MitarbeiterPresentation>() {
            @Override public MitarbeiterPresentation convert(final Benutzer mitarbeiter) {
                return new MitarbeiterPresentation(mitarbeiter.getLogin(), mitarbeiter.isAdmin());
            }
        }));
        this.em = em;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public Class<BenutzerList> getActionType() {
        return BenutzerList.class;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override @Secured @Transactional
    public PartResult<Benutzer> executeInternal(final BenutzerList action) throws ActionException {
        final EntityManager em = this.em.get();


        Query qCount = createQuery(action, em, "select count(*) ", "");

        long anz = (Long) qCount.getSingleResult();

        Query q = createQuery(action, em, "", " order by login");
        q.setFirstResult(action.getStartEntry());
        q.setMaxResults(action.getPageRange());

        return createPartResult(action.getStartEntry(), (int) anz, q.getResultList());
    }

    private Query createQuery(final BenutzerList action, final EntityManager em, final String prefix, final String suffix) {
        String query = "";
        Map<String, Object> params = new HashMap<String, Object>();

        String t = action.getFilter().getText();
        if (t != null) {
            query = (query.isEmpty() ? "" : query + " and ") + " (lower(login) like :t or  lower(email) like :t) ";
            params.put("t", "%" + t.trim().toLowerCase() + "%");
        }

        Query q = em.createQuery(prefix + " from Benutzer " + (query.isEmpty() ? "" : "WHERE " + query) + " " + suffix);

        for (Map.Entry<String, Object> stringObjectEntry : params.entrySet()) {
            q.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return q;
    }
}