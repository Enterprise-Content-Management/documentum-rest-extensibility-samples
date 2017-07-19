/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.dfc;

import java.util.Arrays;
import java.util.List;

import com.emc.documentum.rest.dfc.query.PagedQueryTemplate;
import com.emc.documentum.rest.sort.SortOrder;

/**
 * A query template to get name card collection.
 */
public class NameCardCollectionQueryTemplate extends PagedQueryTemplate {
    @Override
    protected List<String> defaultFields() {
        return Arrays.asList(
                "r_object_id", "r_object_type", "object_name", "title", "r_content_size", "owner_name",
                "r_creation_date", "r_modify_date",
                "contact_aspect.alias", "contact_aspect.company", "contact_aspect.department", "contact_aspect.email",
                "contact_aspect.mobile", "contact_aspect.office", "contact_aspect.street",
                "organization_aspect.manager", "organization_aspect.direct_reports"
        );
    }

    @Override
    protected String qualification() {
        return "";
    }

    @Override
    protected String from() {
        return "name_card";
    }

    @Override
    protected List<SortOrder> defaultSorts() {
        return Arrays.asList(new SortOrder("object_name", true));
    }

    @Override
    protected boolean supportRepeatingAttributeQuery() {
        return true;
    }
}
