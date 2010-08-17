package de.atns.common.security.server;

import com.wideplay.warp.persist.dao.Finder;
import de.atns.common.security.model.Benutzer;

/**
 * @author tbaum
 * @since 17.08.2010
 */
public interface BenutzerRepository {
// -------------------------- OTHER METHODS --------------------------

    @Finder(query = "SELECT m FROM Benutzer m WHERE login = ?") Benutzer mitarbeiterByLogin(String login);
}
