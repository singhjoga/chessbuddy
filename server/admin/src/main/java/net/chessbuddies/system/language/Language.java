package net.chessbuddies.system.language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;
import com.technovator.api.common.annotations.LifecycleStatus;
import com.technovator.api.common.constants.Operations;
import com.technovator.api.common.constants.RegEx;
import com.technovator.api.common.constants.Views;
import com.technovator.api.common.domain.IdentifiableEntity;

@Entity(name="LANG")
@Schema(name = "Supported languages")
public class Language implements IdentifiableEntity<String>{

	@Id
	@Column(name="CODE")
	@Size(min = 3, max = 20, groups=Operations.Always.class)
	@Schema(description = "Reference Code. Use as a Key other places for the references", required=true)
	@Pattern(regexp = RegEx.NAME,groups=Operations.Always.class)
	@NotNull(groups=Operations.Add.class)
	@JsonView(value = {Views.List.class, Views.Add.class})
	private String id;

	@Column(name="EN_NAME")
	@Size(min = 1, max = 50, groups=Operations.Always.class)
	@Schema(description = "Language name in English", required=true)
	@NotNull(groups=Operations.Add.class)
	@JsonView(value= {Views.Allways.class})
	private String englishName;

	@Column(name="LOCAL_NAME")
	@Size(min = 1, max = 50, groups=Operations.Always.class)
	@Schema(description = "Language name in local name", required=true)
	@NotNull(groups=Operations.Add.class)
	@JsonView(value= {Views.Allways.class})
	private String localName;

	@Column(name="IS_DISABLED")
	@Schema(description = "'true' if the entry is disabled i.e. not in use",example = "false")
	@JsonView(value= {Views.Update.class,Views.List.class})
	@LifecycleStatus	
	private Boolean isDisabled;	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

}
