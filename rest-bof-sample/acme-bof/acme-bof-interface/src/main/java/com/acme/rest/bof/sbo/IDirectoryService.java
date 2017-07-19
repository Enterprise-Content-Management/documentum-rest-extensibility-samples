/*
 * Copyright (c) 2017. EMC Coporation. All Rights Reserved.
 */

package com.acme.rest.bof.sbo;

import java.io.File;
import java.util.Map;

import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfService;
import com.documentum.fc.common.DfException;

/**
 * Interface for business directory service.
 */
public interface IDirectoryService extends IDfService {
    IDfPersistentObject create(String repositoryName, Map<String, Object> attrs, File bio, File photo) throws DfException;
}
