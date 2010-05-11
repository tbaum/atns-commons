package de.atns.common.mail;

public interface MailTemplate {
// -------------------------- OTHER METHODS --------------------------

    String getHtmlText();


    String getSenderEmail();

    String getSenderName();

    String getSubject();

    String getText();

    boolean isHtmlMail();
}
