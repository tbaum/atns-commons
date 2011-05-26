package de.atns.common.mail;

import com.google.inject.ImplementedBy;

import java.util.HashMap;
import java.util.Map;

@ImplementedBy(MailUtilImpl.class)
public interface MailUtil {
// -------------------------- OTHER METHODS --------------------------

    EmailMessage sendMail(String recipient, String recipientName,
                          MailTemplate template, HashMap<String, Object> context,
                          MailTemplateResource... attachments);

    EmailMessage sendMail(String recipient, String recipientName, String ccRecipient,
                          MailTemplate template, HashMap<String, Object> context,
                          MailTemplateResource... attachments);

    EmailMessage sendMail(String recipient, String recipientName, String ccRecipient, String bccRecipient,
                          MailTemplate template, Map<String, Object> context,
                          MailTemplateResource... attachments);

    EmailMessage sendMail(String recipient, String recipientName, String ccRecipient, String bccRecipient,
                          MailTemplate template, Map<String, Object> context,
                          EmailResource... attachments);
}
