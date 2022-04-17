package com.technovator.api.common.auditlog;

import java.io.Serializable;

public interface AuditableMain<ID extends Serializable> extends Auditable{
	String getName();
	ID getId();
}
