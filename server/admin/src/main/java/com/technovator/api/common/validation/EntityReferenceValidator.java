package com.technovator.api.common.validation;

import com.technovator.api.common.annotations.EntityReference;
import com.technovator.api.common.domain.IdentifiableEntity;

public class EntityReferenceValidator extends BaseEntityReferenceValidator<EntityReference>{

	@Override
	protected Class<? extends IdentifiableEntity<?>> getValue(EntityReference annotation) {
		return annotation.value();
	}

}
