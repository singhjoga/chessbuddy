package org.upkaari.api.common.auditlog;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AuditableMain<ID> extends Auditable{
	@JsonIgnore
	String getName();
	ID getId();
}
