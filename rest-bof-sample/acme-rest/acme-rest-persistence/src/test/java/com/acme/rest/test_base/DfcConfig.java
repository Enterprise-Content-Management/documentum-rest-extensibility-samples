/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.test_base;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public enum DfcConfig {
    INSTANCE {
        protected String configFile() {
            return "dfc.properties";
        }
    };

    public final String repository() {
        return get().getString("dfc.globalregistry.repository");
    }

    public String username() {
        return get().getString("dfc.globalregistry.username");
    }

    public String password() {
        return get().getString("dfc.globalregistry.password");
    }

    protected abstract String configFile();

    private final Configuration config = loadConfig(configFile());

    public final Configuration get() {
        return config;
    }

    private Configuration loadConfig(String configFile) {
        Configuration appConfig = null;
        try {
            appConfig = new PropertiesConfiguration(configFile);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        return appConfig;
    }
}

