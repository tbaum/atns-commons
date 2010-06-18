package de.atns.common.mail;

import com.wideplay.warp.persist.dao.Finder;
import de.atns.common.mail.EmailMessage;

import java.util.List;

/**
 * @author tbaum
 * @since 12.06.2010
 */
public interface EmailRepository {
       @Finder(query = "from EmailMessage where sent is null AND error is null") List<EmailMessage> getAllUnsentMails();
}