package org.upkaari.api.components.work;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.upkaari.api.common.auditlog.AuditableMain;
import org.upkaari.api.common.domain.AbstractResource;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "WORK")
public class Work extends AbstractResource<String> implements AuditableMain<String>{
	private String name;
	private String description;

    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}
	
    @JsonIgnore
	@Override
	public String getAppObjectType() {
		return this.getClass().getSimpleName();
	}
}
