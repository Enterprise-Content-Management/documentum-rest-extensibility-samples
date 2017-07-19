/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.context.jc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acme.rest.dfc.NameCardManager;
import com.acme.rest.dfc.impl.NameCardManagerImpl;

@Configuration
public class PersistenceConfig {
    @Bean
    public NameCardManager nameCardManager() {
        return new NameCardManagerImpl();
    }
}
