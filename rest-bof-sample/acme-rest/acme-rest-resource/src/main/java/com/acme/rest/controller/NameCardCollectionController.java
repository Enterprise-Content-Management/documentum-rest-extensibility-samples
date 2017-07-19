/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.acme.rest.dfc.NameCardCollectionQueryTemplate;
import com.acme.rest.dfc.NameCardManager;
import com.acme.rest.model.NameCard;
import com.acme.rest.view.impl.NameCardFeedView;
import com.acme.rest.view.impl.NameCardView;
import com.documentum.fc.common.DfException;
import com.emc.documentum.rest.controller.AbstractController;
import com.emc.documentum.rest.dfc.query.PagedQueryTemplate;
import com.emc.documentum.rest.http.SupportedMediaTypes;
import com.emc.documentum.rest.http.UriInfo;
import com.emc.documentum.rest.http.annotation.RequestUri;
import com.emc.documentum.rest.http.annotation.TypedParam;
import com.emc.documentum.rest.http.parameter.CollectionParam;
import com.emc.documentum.rest.model.AtomFeed;
import com.emc.documentum.rest.paging.PagedDataRetriever;
import com.emc.documentum.rest.paging.PagedPersistentDataRetriever;
import com.emc.documentum.rest.view.ViewParams;
import com.emc.documentum.rest.view.annotation.ResourceViewBinding;

/**
 * Collection of name cards in a repository.
 */
@Controller("acme#name-cards")
@RequestMapping("/repositories/{repositoryName}/name-cards")
@ResourceViewBinding({NameCardFeedView.class, NameCardView.class})
public class NameCardCollectionController extends AbstractController {

    @Autowired
    NameCardManager nameCardManager;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {
                    SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_STRING,
                    MediaType.APPLICATION_JSON_VALUE,
            }
    )
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AtomFeed get(
            @PathVariable("repositoryName") final String repositoryName,
            @TypedParam final CollectionParam param,
            @RequestUri final UriInfo uriInfo)
            throws Exception {
        PagedQueryTemplate template = new NameCardCollectionQueryTemplate()
                .filter(param.getFilterQualification())
                .order(param.getSortSpec().get())
                .page(param.getPagingParam().getPage(), param.getPagingParam().getItemsPerPage());
        PagedDataRetriever<NameCard> dataRetriever = new PagedPersistentDataRetriever<NameCard>(
                template,
                param.getPagingParam().getPage(),
                param.getPagingParam().getItemsPerPage(),
                param.getPagingParam().isIncludeTotal(),
                param.getAttributeView(),
                NameCard.class);

        return getRenderedPage(repositoryName, dataRetriever.get(), param.isLinks(), param.isInline(), uriInfo, null);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {
                SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_STRING,
                MediaType.APPLICATION_JSON_VALUE
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @ResourceViewBinding(NameCardView.class)
    public NameCard create(
            @PathVariable("repositoryName") final String repositoryName,
            @RequestBody final NameCard nameCard,
            @RequestUri final UriInfo uriInfo) throws DfException {
        validateTargetControllerAccessible(NameCardController.class);
        NameCard createdAliaSet = nameCardManager.create(nameCard);
        Map<String, Object> others = Collections.singletonMap(ViewParams.POST_FROM_COLLECTION, (Object) true);
        return getRenderedObject(repositoryName, createdAliaSet, true, uriInfo, others);
    }
}