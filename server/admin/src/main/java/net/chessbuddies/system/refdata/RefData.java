package net.chessbuddies.system.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.technovator.api.common.constants.Views;
import com.technovator.api.common.domain.IdentifiableEntity;
import com.technovator.api.common.translation.TranslationSerializer;

@Entity(name="REF_DATA")
public class RefData implements IdentifiableEntity<String>{

	@Id
	@Column(name="CODE")
	@JsonView(value= {Views.Add.class,Views.List.class})
	private String code;

	@Column(name="TYPE_CODE")
	@JsonView(value= {Views.Add.class,Views.List.class})
	private String referenceType;
	
	@Column(name="VALUE")
	@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
	@JsonSerialize(using = TranslationSerializer.class)
	private String value;
	
	@Column(name="DESCRIPTION")
	@JsonView(value= {Views.Add.class,Views.Update.class,Views.List.class})
	private String description;

	@Column(name="IS_DISABLED")
	@JsonView(value= {Views.Update.class,Views.List.class})
	private Boolean isDisabled;
	
	@Column(name="DISPLAY_ORDER")
	@JsonView(value= {Views.Update.class,Views.List.class})
	private Integer displayOrder;	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getReferenceType() {
		return referenceType;
	}
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	@Override
	public String getId() {
		return code;
	}
	@Override
	public void setId(String id) {
		this.code=id;
	}
}
