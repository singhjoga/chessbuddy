package com.technovator.api.common.translation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.technovator.api.common.constants.Constants;
import com.technovator.api.common.services.BaseService;

import io.quarkus.arc.Unremovable;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Unremovable
@Slf4j
public class TranslationService extends BaseService{
	private Map<String, String> translations;
	
	@Inject
	private TranslationRepo repo;
	@Inject
	private TranslationValueRepo valueRepo;
	public TranslationService(TranslationRepo repo) {
		this.repo=repo;
	}
	/**
	 * Init. Being called from Statrup Initializer
	 */
	public void init() {
		translations = new HashMap<>();
		List<TranslationValue> values = findAllByIsDisabled(false);
		for (TranslationValue value: values) {
			translations.put(getKey(value.getTranslationKey(), value.getLanguageCode()), value.getValue());
		}
		log.info("Translations initialized");
	}
	public List<TranslationValue> findAllByIsDisabled(Boolean isDisabled){
		return valueRepo.findAll().list();
	}
	
	public String findTranslation(String translationKey, String langCode) {
		if (StringUtils.isBlank(langCode)) {
			langCode = Constants.ENGLISH_LANGUAGE_CODE;
		}
		String key = getKey(translationKey, langCode);
		String value = translations.get(key);
		if (value != null) {
			return value;
		}
		log.warn("Tranlations not found for key '{}' language '{}'",translationKey, langCode);
		key = getKey(translationKey, Constants.ENGLISH_LANGUAGE_CODE);
		value = translations.get(key);
		if (value == null) {
			throw new IllegalStateException(String.format("Tranlations not found for key '%s' language '%s'",translationKey, langCode));
		}
		return value;
	}
	
	private String getKey(String translationKey, String langCode) {
		return langCode+":"+translationKey;
	}
}
