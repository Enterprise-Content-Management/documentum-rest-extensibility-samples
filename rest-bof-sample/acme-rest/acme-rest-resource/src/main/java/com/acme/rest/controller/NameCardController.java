/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.controller;

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

import com.acme.rest.dfc.NameCardManager;
import com.acme.rest.model.NameCard;
import com.acme.rest.view.impl.NameCardView;
import com.documentum.fc.common.DfException;
import com.emc.documentum.rest.controller.AbstractController;
import com.emc.documentum.rest.http.SupportedMediaTypes;
import com.emc.documentum.rest.http.UriInfo;
import com.emc.documentum.rest.http.annotation.RequestUri;
import com.emc.documentum.rest.http.annotation.TypedParam;
import com.emc.documentum.rest.http.parameter.SingleParam;
import com.emc.documentum.rest.utils.NameAsPathCoder;
import com.emc.documentum.rest.view.annotation.ResourceViewBinding;

/**
 * A name card.
 */
@Controller("acme#name-card")
@RequestMapping("/repositories/{repositoryName}/name-cards/{name}")
@ResourceViewBinding(value = NameCardView.class, queryTypes = "name_card")
public class NameCardController extends AbstractController {
    @Autowired
    NameCardManager nameCardManager;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {
                    SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_STRING,
                    MediaType.APPLICATION_JSON_VALUE
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public NameCard get(
            @PathVariable("repositoryName") final String repositoryName,
            @PathVariable("name") final String name,
            @TypedParam final SingleParam param,
            @RequestUri final UriInfo uriInfo)
            throws Exception {
        NameCard aliasSet = nameCardManager.get(NameAsPathCoder.decode(name), param.getAttributeView());
        return getRenderedObject(repositoryName, aliasSet, param.isLinks(), uriInfo, null);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = {
                SupportedMediaTypes.APPLICATION_VND_DCTM_JSON_STRING,
                MediaType.APPLICATION_JSON_VALUE
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public NameCard update(
            @PathVariable("repositoryName") final String repositoryName,
            @PathVariable("name") final String name,
            @RequestBody NameCard nameCard,
            @RequestUri final UriInfo uriInfo) throws DfException {
        NameCard updated = nameCardManager.update(NameAsPathCoder.decode(name), nameCard);
        return getRenderedObject(repositoryName, updated, true, uriInfo, null);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("name") final String name) throws DfException {
        nameCardManager.remove(NameAsPathCoder.decode(name));
    }
}
