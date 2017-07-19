/*
 * Copyright (c) 2017. EMC Coporation. All Rights Reserved.
 */

package com.acme.rest.bof.aspect;

import com.documentum.fc.client.DfDocument;
import com.documentum.fc.common.DfException;

/**
 * Aspect implementation for 'organization_aspect'.
 */
public class OrgAspect extends DfDocument implements IOrgAspect {
    public static final String NAME = "organization_aspect";

    public void setManager(String name) throws DfException {
        this.setString(NAME + ".manager", name);
    }

    public void addDirectReports(String... names) throws DfException {
        if (names != null) {
            this.removeAll(NAME + ".direct_reports");
            for (String name: names) {
                this.appendString(NAME + ".direct_reports", name);
            }
        }
    }
}
