package org.upkaari.api.common.auditlog;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.upkaari.api.common.domain.AbstractResource;
import org.upkaari.api.common.services.BaseService;
import org.upkaari.api.common.utils.CommonUtil;

@ApplicationScoped
public class AuditLogService extends BaseService{
	@Inject
	private AuditLogRepository repo;
	public String add(String action, AuditableMain<?> auditable, String details) {
		return add(action,auditable, details, null);
	}
	public String add(String action, AuditableMain<?> auditable, String details, String filter) {
		if (StringUtils.isEmpty(details)) {
			return null;
		}
		AuditLog obj = new AuditLog();
		obj.setId(CommonUtil.genUUID());
		obj.setAction(action);
		obj.setDate(new Date());
		obj.setDetails(details);
		obj.setObjectType(auditable.getAppObjectType());
		obj.setObjectId(auditable.getId().toString());
		obj.setObjectName(auditable.getName());
		obj.setUser(getLoggedUser());
		obj.setFilterValue(filter);
		repo.persist(obj);
		
		return obj.getId();
	}
	
	public List<AuditLog> find(String entity, String entityId) {
		return repo.findByObjectTypeAndObjectIdOrderByDateDesc(entity, entityId);
	}
	public List<AuditLog> find(AuditableMain<?> auditable) {
		return find(auditable,null);
	}
	public List<AuditLog> find(AuditableMain<?> auditable, List<AuditLog> childAuditables) {
		List<AuditLog> history = repo.findByObjectTypeAndObjectIdOrderByDateDesc(auditable.getAppObjectType(), auditable.getId().toString());
	/*	addToResult(history, auditable);
		if (childAuditables != null && !childAuditables.isEmpty()) {
			Comparator<ChangeHistory> comp = Comparator.comparing(ChangeHistory::getDate).reversed();
			history.addAll(childAuditables);
			history.sort(comp);
		}
		*/
		return history; 
	}
	
	private void addToResult(List<AuditLog> history, AuditableMain<?> auditable) {
		//add the added record, which is not part of the history to preserve space
		AuditLog add = new AuditLog();
		if (auditable instanceof AbstractResource) { //this the case always
			AbstractResource common = (AbstractResource) auditable;
			if (common.getCreateDate()==null) {
				return;
			}
			add.setDate(common.getCreateDate());
			add.setUser(common.getCreateUser());
		}
		add.setAction("Add");
		add.setDetails("Added");
		add.setObjectId(auditable.getId().toString());
		add.setObjectName(auditable.getName());
		add.setObjectType(auditable.getAppObjectType());
		history.add(add);
	}
}
