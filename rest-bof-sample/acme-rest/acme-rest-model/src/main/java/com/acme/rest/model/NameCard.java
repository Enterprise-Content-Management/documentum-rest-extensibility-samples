/*
 * Copyright (c) 2017. OpenText Corporation. All Rights Reserved.
 */

package com.acme.rest.model;

import java.util.Arrays;
import java.util.List;

import com.emc.documentum.rest.binding.SerializableType;
import com.emc.documentum.rest.model.ContentfulObject;

/**
 * Data model for name_card
 */
@SerializableType("name-card")
public class NameCard extends ContentfulObject {

    public NameCard() {
        setType("name_card");
    }

    public String getName() {
        return (String) getMandatoryAttribute("object_name");
    }

    public String getOffice() {
        return (String) getMandatoryAttribute("contact_aspect.office");
    }

    public String getCompany() {
        return (String) getMandatoryAttribute("contact_aspect.company");
    }

    public String getManager() {
        return (String) getMandatoryAttribute("organization_aspect.manager");
    }

    @SuppressWarnings("unchecked")
    public List<String> getDirectReports() {
        return (List<String>) getMandatoryAttribute("organization_aspect.direct_reports");
    }

    @Override
    public List<String> getMandatoryFields() {
        return Arrays.asList("r_object_id", "r_object_type", "object_name", "title", "r_content_size", "owner_name",
                "r_creation_date", "r_modify_date", "contact_aspect.alias", "contact_aspect.company",
                "contact_aspect.department", "contact_aspect.email", "contact_aspect.mobile", "contact_aspect.office",
                "contact_aspect.street", "organization_aspect.manager", "organization_aspect.direct_reports"
        );
    }
}
