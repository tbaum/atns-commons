package de.atns.common.security.benutzer.server;

import com.google.inject.Inject;
import de.atns.common.dao.PartResult;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.gwt.server.ConvertingActionHandler;
import de.atns.common.security.Secured;
import de.atns.common.security.benutzer.client.action.BenutzerList;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import de.atns.common.security.model.Benutzer;
import de.atns.common.security.server.BenutzerRepository;
import net.customware.gwt.dispatch.shared.ActionException;

import static de.atns.common.dao.PartResult.createPartResult;
import static de.atns.common.gwt.server.ListConverter.listConverter;
import static de.atns.common.security.benutzer.server.BenutzerPresentationConverter.BENUTZER_CONVERTER;
import static de.atns.common.security.model.DefaultRoles.ADMIN;


/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerListHandler
        extends ConvertingActionHandler<BenutzerList, ListPresentation<BenutzerPresentation>, PartResult<Benutzer>> {
// ------------------------------ FIELDS ------------------------------

    private final BenutzerRepository repository;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerListHandler(final BenutzerRepository repository) {
        super(listConverter(BENUTZER_CONVERTER));
        this.repository = repository;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public Class<BenutzerList> getActionType() {
        return BenutzerList.class;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override @Secured(ADMIN)
    public PartResult<Benutzer> executeInternal(final BenutzerList action) throws ActionException {
        final String text = action.getFilter().getFilterText();
        if (text != null && !text.isEmpty()) {
            return createPartResult(action.getStartEntry(), repository.countBenutzer(text),
                    repository.findBenutzer(text, action.getStartEntry(), action.getPageRange()));
        } else {
            return createPartResult(action.getStartEntry(), repository.countAllBenutzer(),
                    repository.findAllBenutzer(action.getStartEntry(), action.getPageRange()));
        }
    }
}
