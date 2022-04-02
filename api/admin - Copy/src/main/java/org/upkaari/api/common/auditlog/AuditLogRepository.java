package org.upkaari.api.common.auditlog;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AuditLogRepository implements PanacheRepository<AuditLog>{
	public List<AuditLog> findByObjectTypeAndObjectIdOrderByDateDesc(String resource, String resourceId) {
		return this.list("objectType=? and objectId=?", resource, resourceId);
	}
}
