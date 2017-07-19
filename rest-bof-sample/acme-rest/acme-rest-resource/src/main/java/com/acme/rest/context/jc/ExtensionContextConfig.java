/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.context.jc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.acme.rest",
        excludeFilters = { @ComponentScan.Filter(type = FilterType.CUSTOM,
        value = { com.emc.documentum.rest.context.ComponentScanExcludeFilter.class }) })
public class ExtensionContextConfig {

}
