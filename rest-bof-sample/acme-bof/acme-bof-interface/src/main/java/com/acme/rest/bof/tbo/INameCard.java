/*
 * Copyright (c) 2017. EMC Coporation. All Rights Reserved.
 */

package com.acme.rest.bof.tbo;

import com.documentum.fc.client.IDfBusinessObject;
import com.documentum.fc.common.DfException;

/**
 * Object interface for custom type 'name_card'.
 */
public interface INameCard extends IDfBusinessObject {
    void setHasContact(boolean hasContact) throws DfException;
    void setHasOrganization(boolean hasOrganization) throws DfException;
}
