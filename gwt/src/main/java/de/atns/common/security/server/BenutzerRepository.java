package de.atns.common.security.server;

import com.google.inject.name.Named;
import com.google.inject.persist.finder.Finder;
import com.google.inject.persist.finder.FirstResult;
import com.google.inject.persist.finder.MaxResults;
import de.atns.common.security.model.Benutzer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tbaum
 * @since 17.08.2010
 */
public interface BenutzerRepository {
// ------------------------------ FIELDS ------------------------------

    String QRY_BENUTZER = "(lower(login) like lower(:aLogin) or  lower(email) like lower(:aLogin))";

// -------------------------- OTHER METHODS --------------------------

    @Finder(query = "SELECT m FROM Benutzer m WHERE login = :aLogin") Benutzer benutzerByLogin(
            @Named("aLogin") String login);

    @Finder(query = "SELECT count(distinct b) FROM Benutzer b ") Long countAllBenutzer();

    @Finder(query = "SELECT count(distinct b) FROM Benutzer b where " + QRY_BENUTZER) Long countBenutzer(
            @Named("aLogin") String name);

    @Finder(query = "SELECT distinct b FROM Benutzer b order by b.login", returnAs = ArrayList.class)
    List<Benutzer> findAllBenutzer(
            @FirstResult int start, @MaxResults int paging);

    @Finder(query = "SELECT distinct b FROM Benutzer b where " + QRY_BENUTZER + " order by b.login",
            returnAs = ArrayList.class)
    List<Benutzer> findBenutzer(@Named("aLogin") String name, @FirstResult int start, @MaxResults int paging);
}
