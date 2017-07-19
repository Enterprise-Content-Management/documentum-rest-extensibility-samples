/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.test_base;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emc.documentum.rest.config.RepositoryContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/META-INF/spring/*.xml" })
public abstract class PersistenceTestBase {

    @Before
    public void setup() throws Exception {
        //setup login
        RepositoryContextHolder.setLoginName(DfcConfig.INSTANCE.username());
        RepositoryContextHolder.setPassword(DfcConfig.INSTANCE.password());
        RepositoryContextHolder.setRepositoryName(DfcConfig.INSTANCE.repository());
    }

    @After
    public void clear() {
        RepositoryContextHolder.clear();
    }
}
