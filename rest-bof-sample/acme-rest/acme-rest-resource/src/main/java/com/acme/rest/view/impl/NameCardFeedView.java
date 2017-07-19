/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.view.impl;

import java.util.Date;
import java.util.Map;

import com.acme.rest.model.NameCard;
import com.emc.documentum.rest.http.UriInfo;
import com.emc.documentum.rest.paging.Page;
import com.emc.documentum.rest.view.annotation.FeedViewBinding;

/**
 * view for alias sets.
 */
@FeedViewBinding(NameCardView.class)
public class NameCardFeedView extends com.emc.documentum.rest.view.FeedableView {

    public NameCardFeedView(Page<NameCard> page, UriInfo uriInfo, String repositoryName, Boolean returnLinks, Map<String, Object> others) {
        super(page, uriInfo, repositoryName, returnLinks, others);
    }

    @Override
    public String feedTitle() {
        return "Name Cards";
    }

    @Override
    public Date feedUpdated() {
        return new Date();
    }
}
