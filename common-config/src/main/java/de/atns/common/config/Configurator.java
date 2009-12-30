package de.atns.common.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Properties;

import static java.net.InetAddress.getLocalHost;

public class Configurator {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(Configurator.class);
    private final Properties configProperties = new Properties();
    private String configName = null;

// -------------------------- OTHER METHODS --------------------------

    public synchronized String get() {
        try {
            final Properties configMapping = new Properties();
            configMapping.load(getClass().getResourceAsStream("mapping.properties"));
            final String hostName = getLocalHost().getHostName();
            String configName = configMapping.getProperty(hostName, "unknown");
            LOG.warn("using config-name " + configName + " for host " + hostName);
            return configName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String get(final String s) {
        if (configName == null) {
            try {
                configName = get();
                configProperties.load(getClass().getResourceAsStream("config.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return configProperties.getProperty(configName + "." + s);
    }
}
