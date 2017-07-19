/*
 * Copyright (c) 2017. EMC Coporation. All Rights Reserved.
 */

package com.acme.rest.bof.aspect;

import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.aspect.IDfAttachAspectCallback;

/**
 * Aspect callback for 'contact_aspect'.
 */
public class ContactAspectCallback implements IDfAttachAspectCallback {
    public void doPostAttach(IDfPersistentObject iDfPersistentObject) throws Exception {
        iDfPersistentObject.setBoolean("has_contact", true);
    }
}
