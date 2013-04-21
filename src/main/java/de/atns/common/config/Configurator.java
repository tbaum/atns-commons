package de.atns.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

import static java.net.InetAddress.getLocalHost;

public class Configurator {

    private static final Logger LOG = LoggerFactory.getLogger(Configurator.class);
    private final Properties configProperties = new Properties();
    private String configName = null;

    public synchronized String get(final String key) {
        ensureLoaded();
        return configProperties.getProperty(getPropertyName(key));
    }

    private synchronized void ensureLoaded() {
        if (configName == null) {
            try {
                configName = get();
                configProperties.load(getClass().getResourceAsStream("config.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized String get() {
        try {
            final Properties configMapping = new Properties();
            configMapping.load(getClass().getResourceAsStream("mapping.properties"));
            final String hostName = getLocalHost().getHostName();
            final String configName = configMapping.getProperty(hostName, "unknown");
            LOG.warn("using config-name " + configName + " for host " + hostName);
            return configName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPropertyName(final String s) {
        return configName + "." + s;
    }

    public void put(final String key, final String value) {
        ensureLoaded();
        configProperties.put(getPropertyName(key), value);
    }
}
