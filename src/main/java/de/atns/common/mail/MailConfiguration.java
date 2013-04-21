package de.atns.common.mail;

import java.util.Properties;

/**
 * @author tbaum
 * @since 26.05.11 04:38
 */
public class MailConfiguration extends Properties {

    public MailConfiguration() {
        this(new Properties());
    }

    public MailConfiguration(final Properties properties) {
        super(properties);

        put("mail." + getProtocol() + ".dsn.notify", "FAILURE,DELAY");
        put("mail." + getProtocol() + ".dsn.ret", "HDRS");
    }

    String getProtocol() {
        return isSsl() ? "smtps" : "smtp";
    }

    public boolean isSsl() {
        return "true".equalsIgnoreCase(getProperty("mail.smtp.ssl")) ||
                "true".equalsIgnoreCase(getProperty("mail.smtps.ssl"));
    }

    public String getHost() {
        return (String) get("mail." + getProtocol() + ".host");
    }

    public String getPass() {
        return (String) get("mail." + getProtocol() + ".password");
    }

    public String getUser() {
        return (String) get("mail." + getProtocol() + ".user");
    }
}
