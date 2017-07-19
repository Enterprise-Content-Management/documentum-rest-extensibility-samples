/*
 * Copyright (c) 2017. EMC Coporation. All Rights Reserved.
 */

package com.acme.rest.bof.sbo;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.acme.rest.bof.aspect.ContactAspect;
import com.acme.rest.bof.aspect.OrgAspect;
import com.acme.rest.bof.aspect.OrgAspectCallback;
import com.documentum.fc.client.DfService;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.impl.ISysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfValue;
import com.documentum.fc.common.IDfAttr;

/**
 * Implementation for business directory service.
 */
public class DirectoryService extends DfService implements IDirectoryService {

    public IDfPersistentObject create(String repositoryName, Map<String, Object> attrs,
            File bio, File photo) throws DfException {
        IDfSession session = null;
        try {
            session = getSessionManager().getSession(repositoryName);
            ISysObject obj = (ISysObject) session.newObject("name_card");
            setAspects(obj);
            setAttributes(obj, attrs);
            if (bio != null) {
                obj.setFileEx(bio.getAbsolutePath(), "text", 0, null);
            }
            if (photo != null) {
                obj.setFileEx(photo.getAbsolutePath(), "png", 0, null);
            }
            validate(session, obj);
            obj.save();
            return obj;
        } finally {
            if (session != null) {
                getSessionManager().release(session);
            }
        }
    }

    private void validate(IDfSession session, ISysObject obj) throws DfException {
        if (obj.getObjectName() == null || "".equals(obj.getObjectName())) {
            throw new DfException("Object name cannot be empty for a new name card.");
        }
        IDfPersistentObject po = session.getObjectByQualification("name_card where object_name='" + obj.getObjectName() + "'");
        if (po != null && DfId.isObjectId(po.getObjectId().getId())) {
            throw new DfException("Duplicated name cards with the same object name: " + obj.getObjectName());
        }
    }

    private void setAttributes(ISysObject obj, Map<String, Object> attrs) throws DfException {
        if (attrs == null) return;

        for (int k=0; k<obj.getAttrCount(); k++) {
            IDfAttr attr = obj.getAttr(k);
            String name = attr.getName();
            if (attrs.containsKey(name)) {
                if (attr.isRepeating()) {
                    setRepeatingValues(obj, name, (List) attrs.get(name), attr.getDataType());
                } else {
                    setSingleValue(obj, name, attrs.get(name), attr.getDataType());
                }
            }
        }
    }

    private void setAspects(ISysObject obj) throws DfException {
        if (obj.getAspects().findStringIndex(ContactAspect.NAME) < 0) {
            obj.attachAspect(ContactAspect.NAME);
            obj.setBoolean("has_contact", true);
        }

        if (obj.getAspects().findStringIndex(OrgAspect.NAME) < 0) {
            OrgAspectCallback callback2 = new OrgAspectCallback();
            obj.attachAspect(OrgAspect.NAME);
            obj.setBoolean("has_organization", true);
        }
    }

    private void setSingleValue(ISysObject obj, String name, Object value, int dataType) throws DfException {
        obj.setValue(name, new DfValue(value, dataType));
    }

    private void setRepeatingValues(ISysObject obj, String name, List list, int dataType) throws DfException {
        int count = list == null ? 0 : list.size();
        for(int k=0; k<count; k++) {
            obj.setRepeatingValue(name, k, new DfValue(list.get(k), dataType));
        }
    }
}
