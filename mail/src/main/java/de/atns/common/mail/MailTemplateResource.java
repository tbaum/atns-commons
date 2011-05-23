package de.atns.common.mail;

/**
 * @author tbaum
 * @since 23.05.11 11:52
 */
public interface MailTemplateResource {
// -------------------------- OTHER METHODS --------------------------

    byte[] getData();

    String getMimeType();

    String getName();

    boolean isEmbedded();
}
