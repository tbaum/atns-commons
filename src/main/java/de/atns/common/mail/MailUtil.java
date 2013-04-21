package de.atns.common.mail;

import com.google.inject.ImplementedBy;

import java.util.HashMap;
import java.util.Map;

@ImplementedBy(MailUtilGuiceImpl.class) public interface MailUtil {

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
                          EmailMessageResource... attachments);
}
