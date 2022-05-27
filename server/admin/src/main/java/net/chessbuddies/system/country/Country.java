package net.chessbuddies.system.country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.technovator.api.common.annotations.EntityReference;
import com.technovator.api.common.constants.Operations;
import com.technovator.api.common.constants.RegEx;
import com.technovator.api.common.constants.Views;
import com.technovator.api.common.domain.IdentifiableEntity;
import com.technovator.api.common.translation.TranslationSerializer;

import net.chessbuddies.system.language.Language;

@Entity(name="COUNTRY")
@Schema(name = "Country")
public class Country implements IdentifiableEntity<String>{

	@Id
	@Column(name="CODE")
	@Size(min = 3, max = 20, groups=Operations.Always.class)
	@Schema(description = "Reference Code. Use as a Key other places for the references", required=true)
	@Pattern(regexp = RegEx.NAME,groups=Operations.Always.class)
	@NotNull(groups=Operations.Add.class)
	@JsonView(value = {Views.List.class, Views.Add.class})
	private String id;

	@Column(name="DEF_LANG_CODE")
	@Schema(description = "Default language Id", required=true)
	@NotNull(groups=Operations.Add.class)
	@JsonView(value= {Views.Allways.class})
	@EntityReference(value = Language.class)
	private String defaultLanguageId;

	@Column(name="EN_NAME")
	@Schema(description = "Name in English", required=true)
	@NotNull(groups=Operations.Add.class)
	@JsonView(value= {Views.Allways.class})
	private String englishName;

	@Column(name="LOCAL_NAME")
	@Schema(description = "Name in local language", required=false)
	@NotNull(groups=Operations.Add.class)
	@JsonView(value= {Views.Allways.class})
	@JsonSerialize(using = TranslationSerializer.class)
	private String localName;
	
	@Column(name="DATE_FORMAT")
	@Schema(description = "Date format for UI", required=true)
	@NotNull(groups=Operations.Add.class)
	@JsonView(value= {Views.Allways.class})
	private String dateFormat;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDefaultLanguageId() {
		return defaultLanguageId;
	}
	public void setDefaultLanguageId(String defaultLanguageId) {
		this.defaultLanguageId = defaultLanguageId;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}

}
