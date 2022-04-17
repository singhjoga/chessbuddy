package com.technovator.api.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AppObect {
	@JsonIgnore
	String getAppObjectType();
}
