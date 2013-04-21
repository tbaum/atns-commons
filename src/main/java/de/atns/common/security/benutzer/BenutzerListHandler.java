package de.atns.common.security.benutzer;

import ch.lambdaj.function.convert.Converter;
import com.google.inject.Inject;
import com.google.inject.extensions.security.Secured;
import com.google.inject.persist.Transactional;
import de.atns.common.dao.PartResult;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.gwt.server.DefaultActionHandler;
import de.atns.common.security.BenutzerRepository;
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
public class BenutzerListHandler extends DefaultActionHandler<BenutzerList, ListPresentation<UserPresentation>> {

    private final BenutzerRepository repository;
    private final Converter<PartResult<Benutzer>, ListPresentation<UserPresentation>> converter;

    @Inject public BenutzerListHandler(final BenutzerRepository repository, final UserConverter converter) {
        this.repository = repository;
        this.converter = listConverter(converter);
    }

    @Override @Transactional @Secured(UserAdminRole.class)
    public ListPresentation<UserPresentation> executeInternal(final BenutzerList action) {
        PartResult<Benutzer> result;
        final String text = action.getFilter().getFilterText();
        if (text != null && !text.isEmpty()) {
            result = createPartResult(action.getStartEntry(), repository.countBenutzer(text),
                    repository.findBenutzer(text, action.getStartEntry(), action.getPageRange()));
        } else {
            result = createPartResult(action.getStartEntry(), repository.countAllBenutzer(),
                    repository.findAllBenutzer(action.getStartEntry(), action.getPageRange()));
        }
        return converter.convert(result);
    }
}
