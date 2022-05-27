package com.technovator.api.common.translation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;
import com.technovator.api.common.constants.Views;

@Entity(name="TRANS")
@Schema(name = "Translation keya")
public class Translation{

	@Id
	@Column(name="TRANS_KEY")
	@Schema(description = "Translation key", required=true)
	@JsonView(value = {Views.Allways.class})
	private String translationKey;

	@Column(name="TRANS_GRP")
	@Schema(description = "Group", required=true)
	@JsonView(value = {Views.Allways.class})
	private String group;
	
	@Column(name="DESCRIPTION")
	@Schema(description = "Description", required=true)
	@JsonView(value= {Views.Allways.class})
	private String description;

	public String getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
