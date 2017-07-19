/*
 * Copyright (c) 2017. EMC Coporation. All Rights Reserved.
 */

package com.acme.rest.bof.aspect;

import com.documentum.fc.client.DfDocument;
import com.documentum.fc.common.DfException;

/**
 * Aspect implementation for 'contact_aspect'.
 */
public class ContactAspect extends DfDocument implements IContactAspect {
    public static final String NAME = "contact_aspect";

    public void setAlias(String name) throws DfException {
        this.setString(NAME + ".alias", name);
    }

    public void setCompany(String name) throws DfException {
        this.setString(NAME + ".company", name);
    }

    public void setDepartment(String name) throws DfException {
        this.setString(NAME + ".department", name);
    }

    public void setEmail(String name) throws DfException {
        this.setString(NAME + ".email", name);
    }

    public void setMobile(String name) throws DfException {
        this.setString(NAME + ".mobile", name);
    }

    public void setOffice(String name) throws DfException {
        this.setString(NAME + ".office", name);
    }

    public void setStreet(String name) throws DfException {
        this.setString(NAME + ".street", name);
    }
}
