package org.upkaari.api.common.validation;

import org.upkaari.api.common.annotations.EntityReference;
import org.upkaari.api.common.domain.IdentifiableEntity;

public class EntityReferenceValidator extends BaseEntityReferenceValidator<EntityReference>{

	@Override
	protected Class<? extends IdentifiableEntity<?>> getValue(EntityReference annotation) {
		return annotation.value();
	}

}
