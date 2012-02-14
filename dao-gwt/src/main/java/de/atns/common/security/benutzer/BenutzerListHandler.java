package de.atns.common.security.benutzer;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import de.atns.common.dao.PartResult;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.gwt.server.ConvertingActionHandler;
import de.atns.common.security.BenutzerRepository;
import de.atns.common.security.Secured;
import de.atns.common.security.benutzer.client.action.BenutzerList;
import de.atns.common.security.client.model.UserAdminRole;
import de.atns.common.security.client.model.UserPresentation;
import de.atns.common.security.model.Benutzer;

import static de.atns.common.dao.PartResult.createPartResult;
import static de.atns.common.gwt.server.ListConverter.listConverter;

/**
 * @author tbaum
 * @since 23.10.2009
 */
public class BenutzerListHandler extends ConvertingActionHandler<BenutzerList, ListPresentation<UserPresentation>, PartResult<Benutzer>> {
// ------------------------------ FIELDS ------------------------------

    private final BenutzerRepository repository;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerListHandler(final BenutzerRepository repository) {
        super(listConverter(UserConverter.USER_CONVERTER), BenutzerList.class);
        this.repository = repository;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    @Transactional
    @Secured(UserAdminRole.class)
    public PartResult<Benutzer> executeInternal2(final BenutzerList action) {
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
