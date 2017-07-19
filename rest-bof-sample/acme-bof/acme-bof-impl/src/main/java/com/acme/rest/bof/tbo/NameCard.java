/*
 * Copyright (c) 2017. EMC Coporation. All Rights Reserved.
 */

package com.acme.rest.bof.tbo;

import java.io.InputStream;

import com.acme.rest.bof.aspect.ContactAspect;
import com.acme.rest.bof.aspect.ContactAspectCallback;
import com.acme.rest.bof.aspect.OrgAspect;
import com.acme.rest.bof.aspect.OrgAspectCallback;
import com.documentum.fc.client.DfDocument;
import com.documentum.fc.common.DfException;

/**
 * Object implementation for custom type 'name_card'.
 */
public class NameCard extends DfDocument implements INameCard {
    public String getVersion() {
        return "0.1";
    }

    public String getVendorString() {
        return "OpenText";
    }

    public boolean isCompatible(String version) {
        return true;
    }

    public boolean supportsFeature(String feature) {
        return false;
    }

    public void setHasContact(boolean hasContact) throws DfException {
        this.setBoolean("has_contact", hasContact);
    }

    public void setHasOrganization(boolean hasOrganization) throws DfException {
        this.setBoolean("has_organization", hasOrganization);
    }

    public InputStream getBio() throws DfException {
        return this.getStream("text", 0, null, false);
    }

    public InputStream getPhoto() throws DfException {
        return this.getStream("png", 0, null, false);
    }

    @Override
    public synchronized void doSave(boolean saveLock, String versionLabel, Object[] extendedArgs)
        throws DfException {
        this.setString("subject", "Contact card for " + this.getObjectName());

        if (this.getAspects().findStringIndex(ContactAspect.NAME) < 0) {
            ContactAspectCallback callback1 = new ContactAspectCallback();
            this.attachAspect(ContactAspect.NAME, callback1);
        }

        if (this.getAspects().findStringIndex(OrgAspect.NAME) < 0) {
            OrgAspectCallback callback2 = new OrgAspectCallback();
            this.attachAspect(OrgAspect.NAME, callback2);
        }

        super.doSave(saveLock, versionLabel, extendedArgs);
    }
}
