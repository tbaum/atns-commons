package de.atns.common.mail;

public interface MailTemplate {

    String getHtmlText();

    String getSenderEmail();

    String getSenderName();

    String getSubject();

    String getText();

    boolean isAutoText();

    boolean isHtmlMail();
}
