/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.dfc.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.acme.rest.bof.sbo.IDirectoryService;
import com.acme.rest.dfc.NameCardManager;
import com.acme.rest.model.NameCard;
import com.documentum.com.DfClientX;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;
import com.emc.documentum.rest.attributeview.api.AttributeView;
import com.emc.documentum.rest.common.FileUtil;
import com.emc.documentum.rest.config.RepositoryContextHolder;
import com.emc.documentum.rest.constant.CheckinPolicy;
import com.emc.documentum.rest.constant.DeleteVersionPolicy;
import com.emc.documentum.rest.dfc.ContextSessionManager;
import com.emc.documentum.rest.dfc.IDfCloseableSession;
import com.emc.documentum.rest.dfc.SessionAwareAbstractManager;
import com.emc.documentum.rest.dfc.SysObjectManager;
import com.emc.documentum.rest.dfc.VersioningManager;
import com.emc.documentum.rest.dfc.conversion.TypedObjectConverter;
import com.emc.documentum.rest.error.RestClientErrorException;
import com.emc.documentum.rest.model.Attribute;
import com.emc.documentum.rest.model.ContentDataSource;

public class NameCardManagerImpl extends SessionAwareAbstractManager implements NameCardManager {
    @Autowired SysObjectManager sysObjectManager;
    @Autowired VersioningManager versioningManager;
    @Autowired ContextSessionManager contextSessionManager;

    private static DfClientX CLIENT = new DfClientX();
    private static final String BIO = "bio";
    private static final String PHOTO = "photo";

    @Override public NameCard create(NameCard nameCard) throws DfException {
        File bio = null;
        File photo = null;
        try (IDfCloseableSession closeableSession = getSessionRepository().getCloseableSession()) {
            IDirectoryService service = (IDirectoryService) CLIENT.getLocalClient().newService(
                    IDirectoryService.class.getName(),
                    closeableSession.getDfSession().getSessionManager());
            ContentDataSource source = nameCard.nextContent();
            if (source != null && source.getData() != null) {
                bio = FileUtil.toTempFile(source.getData().getInputStream());
            }
            source = nameCard.nextContent();
            if (source != null && source.getData() != null) {
                photo = FileUtil.toTempFile(source.getData().getInputStream());
            }
            IDfPersistentObject card = service.create(
                    RepositoryContextHolder.getRepositoryName(),
                    getAttributesMap(nameCard),
                    bio,
                    photo);
            return new TypedObjectConverter(card, AttributeView.ALL).convert(NameCard.class);
        } catch (DfException | IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            FileUtil.cleanup(bio);
            FileUtil.cleanup(photo);
        }
    }

    @Override public NameCard update(final String name, final NameCard nameCard) throws DfException {
        final String id = validate(name, nameCard);
        nameCard.setId(id);
        return contextSessionManager.executeWithinTheContextTran(
                new ContextSessionManager.SessionCallable<NameCard>() {
                    @Override public NameCard call(IDfSession session) throws DfException {
                        versioningManager.checkOut(id);
                        if (nameCard.hasContent()) {
                            return versioningManager.checkInWithRenditions(
                                    id, nameCard, "", CheckinPolicy.NEXT_MAJOR, true, 0, false, "");
                        } else {
                            return versioningManager.checkIn(
                                    id, nameCard, "", CheckinPolicy.NEXT_MAJOR, true, false, "");
                        }
                    }
                });
    }

    @Override public NameCard get(String name, AttributeView view) throws DfException {
        return getByName(name, view);
    }

    @Override public void remove(String name) throws DfException {
        String id = validate(name);
        sysObjectManager.deleteSysObjectTx(id, true, true, DeleteVersionPolicy.ALL);
    }

    private Map<String, Object> getAttributesMap(NameCard nameCard) {
        Collection<Attribute<?>> attrs = nameCard.getAttributes();
        Map<String, Object> map = new HashMap<>();
        for(Attribute<?> attr : attrs) {
            map.put(attr.getName(), attr.getValue());
        }
        return map;
    }

    private String validate(String name, NameCard nameCard) throws DfException {
        if (nameCard.getName() != null && !name.equals(nameCard.getName())) {
            throw new RestClientErrorException("", new String[] {name}, HttpStatus.BAD_REQUEST, null);
        }
        return validate(name);
    }

    private String validate(String name) throws DfException {
        NameCard nameCard = getByName(name, new AttributeView("r_object_id", "i_chronicle_id"));
        if (nameCard == null) {
            throw new RestClientErrorException("", new String[] {name}, HttpStatus.BAD_REQUEST, null);
        }
        return (String) nameCard.getAttributeByName("i_chronicle_id");
    }

    private NameCard getByName(String name, AttributeView view) throws DfException {
        return sysObjectManager.getObjectByQualification(
                "name_card where object_name='" + name + "'", view, NameCard.class);

    }
}
