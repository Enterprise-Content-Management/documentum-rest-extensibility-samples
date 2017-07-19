/*
 * Copyright (c) 2017. EMC Coporation. All Rights Reserved.
 */

package com.acme.rest.bof.aspect;

import com.documentum.fc.common.DfException;

/**
 * Aspect interface for 'organization_aspect'.
 */
public interface IOrgAspect {
    void setManager(String name) throws DfException;
    void addDirectReports(String... names) throws DfException;
}
