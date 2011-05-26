package de.atns.common.mail;

import java.util.Properties;

/**
 * @author tbaum
 * @since 26.05.11 04:38
 */
public class MailConfiguration extends Properties {
// --------------------------- CONSTRUCTORS ---------------------------

    public MailConfiguration(final Properties properties) {
        super(properties);


        this.put("mail.smtp.dsn.notify", "SUCCESS,FAILURE,DELAY");
        this.put("mail.smtp.dsn.ret", "HDRS");

        if (getHost() != null) {
            this.put(isSsl() ? "mail.smtps.host" : "mail.smtp.host", getHost());
        }
    }

    public boolean isSsl() {
        return "true".equalsIgnoreCase((String) get("mail.smtp.ssl"));
    }

    public String getHost() {
        return (String) get("mail.smtp.host");
    }

// -------------------------- OTHER METHODS --------------------------

    public String getPass() {
        return (String) get("mail.smtp.password");
    }

    public String getUser() {
        return (String) get("mail.smtp.user");
    }
}
