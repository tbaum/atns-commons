package de.atns.common.mail;

import com.google.inject.persist.finder.Finder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tbaum
 * @since 12.06.2010
 */
public interface EmailRepository {

    @Finder(query = "from EmailMessage where sent is null AND error is null", returnAs = ArrayList.class)
    List<EmailMessage> getAllUnsentMails();

    @Finder(query = "from EmailMessage where sent is not null AND error is null", returnAs = ArrayList.class)
    List<EmailMessage> getAllSentMails();
}
