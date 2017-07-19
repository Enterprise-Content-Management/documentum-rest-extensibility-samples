/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.view.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.acme.rest.model.NameCard;
import com.emc.documentum.rest.http.UriInfo;
import com.emc.documentum.rest.model.AtomAuthor;
import com.emc.documentum.rest.view.annotation.DataViewBinding;
import com.emc.documentum.rest.view.impl.PersistentLinkableView;

/**
 * view for name card.
 */
@DataViewBinding(modelType = NameCard.class)
public class NameCardView extends PersistentLinkableView<NameCard> {

    public NameCardView(NameCard aliasSet, UriInfo uriInfo, String repositoryName, Boolean returnLinks,
            Map<String, Object> others) {
        super(aliasSet, uriInfo, repositoryName, returnLinks, others);
    }

    @Override
    public void customize() {
        Integer contentSize = (Integer) getDataInternal().getMandatoryAttribute("r_content_size");
        if (contentSize != null && contentSize > 0) {
            makeLink("biography",
                    idUriBuilder("content-media", getDataInternal().getId())
                            .queryParam("format", "text")
                            .queryParam("page", 0)
                            .queryParam("modifier", "")
                            .build());
            makeLink("photo",
                    idUriBuilder("content-media", getDataInternal().getId())
                            .queryParam("format", "png")
                            .queryParam("page", 0)
                            .queryParam("modifier", "")
                            .build());
        }
        String manager = getDataInternal().getManager();
        if (StringUtils.isNotEmpty(manager)) {
            makeLink("manager",
                    nameUriBuilder("acme#name-card", getDataInternal().getManager())
                            .build());
        }
        List<String> directReports = getDataInternal().getDirectReports();
        if (directReports != null && !directReports.isEmpty()) {
            makeLink("direct-reports",
                    uriBuilder("acme#name-cards")
                            .queryParam("filter", "organization_aspect.manager='" + getDataInternal().getName() + "'")
                            .build());
        }
    }

    @Override
    public String entryTitle() {
        return (String) getDataInternal().getMandatoryAttribute("object_name");
    }

    @Override
    public String entrySummary() {
        StringBuilder builder = new StringBuilder().append(getDataInternal().getMandatoryAttribute("title"));
        String office = (String) getDataInternal().getMandatoryAttribute("contact_aspect.office");
        if (StringUtils.isNotEmpty(office)) {
            builder.append(", ").append(office);
        }
        String company = (String) getDataInternal().getMandatoryAttribute("contact_aspect.company");
        if (StringUtils.isNotEmpty(company)) {
            builder.append(", ").append(company);
        }
        return builder.toString();
    }

    @Override
    public Date entryUpdated() {
        return (Date) getDataInternal().getMandatoryAttribute("r_modify_date");
    }

    @Override
    public Date entryPublished() {
        return (Date) getDataInternal().getMandatoryAttribute("r_creation_date");
    }

    @Override
    public List<AtomAuthor> entryAuthors() {
        String owner = (String) getDataInternal().getMandatoryAttribute("owner_name");
        return Arrays.asList(getAtomAuthor(owner));
    }

    @Override
    public NameCard entryContent() {
        return data();
    }

    @Override
    public String entrySrc() {
        return canonicalResourceUri(true);
    }

    @Override
    public String canonicalResourceUri(boolean validate) {
        return nameUriBuilder("acme#name-card", getDataInternal().getName()).build();
    }
}
