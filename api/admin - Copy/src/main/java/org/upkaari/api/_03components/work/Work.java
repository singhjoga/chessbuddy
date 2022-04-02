package org.upkaari.api._03components.work;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.upkaari.api.common.auditlog.AuditableMain;
import org.upkaari.api.common.domain.AbstractResource;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "SVC")
@Data
public class Work extends AbstractResource<String> implements AuditableMain<String>{
	private String name;
	private String description;
	
    @JsonIgnore
	@Override
	public String getAppObjectType() {
		return this.getClass().getSimpleName();
	}
    @JsonIgnore
	@Override
	public String getName() {
		return name;
	}
}
