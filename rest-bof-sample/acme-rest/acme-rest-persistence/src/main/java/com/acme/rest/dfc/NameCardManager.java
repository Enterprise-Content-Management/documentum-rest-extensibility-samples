/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.dfc;

import com.acme.rest.model.NameCard;
import com.documentum.fc.common.DfException;
import com.emc.documentum.rest.attributeview.api.AttributeView;

public interface NameCardManager {
    NameCard create(NameCard nameCard) throws DfException;
    NameCard update(String name, NameCard nameCard) throws DfException;
    NameCard get(String name, AttributeView view) throws DfException;
    void remove(String name) throws DfException;
}
