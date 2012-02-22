package de.atns.common.mail;

import java.util.Map;

public interface MailUtil {

    MessagePreparator sendMail(String recipient, String recipientName,
                               MailTemplate template, Map<String, Object> context, MailResource... attachments);

    MessagePreparator sendMail(String recipient, String recipientName, String bccRecipient,
                               MailTemplate template, Map<String, Object> context,
                               MailResource... attachments);

    void sendMails();
}
