/*
 * Copyright (c) 2017. EMC Coporation. All Rights Reserved.
 */

package com.acme.rest.bof.aspect;

import com.documentum.fc.common.DfException;

/**
 * Aspect interface for 'contact_aspect'.
 */
public interface IContactAspect {
    void setAlias(String name) throws DfException;
    void setCompany(String name) throws DfException;
    void setDepartment(String name) throws DfException;
    void setEmail(String name) throws DfException;
    void setMobile(String name) throws DfException;
    void setOffice(String name) throws DfException;
    void setStreet(String name) throws DfException;
}
