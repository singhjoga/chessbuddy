package com.technovator.api.common.translation;

import java.io.Serializable;

import javax.persistence.Column;


public class TranslationValueKey implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="TRANS_KEY")
	private String translationKey;

	@Column(name="LANG_CODE")
	private String languageCode;

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

}
