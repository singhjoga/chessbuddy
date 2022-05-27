package com.technovator.api.common.translation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonView;
import com.technovator.api.common.constants.Views;

@Entity(name="TRANS_VAL")
@Schema(name = "Translation values")
@IdClass(value = TranslationValueKey.class)
public class TranslationValue{

	@Id
	@Column(name="TRANS_KEY")
	@Schema(description = "Translation key", required=true)
	@JsonView(value = {Views.Allways.class})
	private String translationKey;

	@Id
	@Column(name="LANG_CODE")
	@Schema(description = "Language code", required=true)
	@JsonView(value = {Views.Allways.class})
	private String languageCode;
	
	@Column(name="VALUE")
	@Schema(description = "Translated value", required=true)
	@JsonView(value= {Views.Allways.class})
	private String value;

	public String getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
