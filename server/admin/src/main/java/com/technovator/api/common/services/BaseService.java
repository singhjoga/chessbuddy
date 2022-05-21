package com.technovator.api.common.services;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.technovator.api.common.cache.SystemCache;
import com.technovator.api.common.context.ClientContext;

public class BaseService{
	@Inject
	private SystemCache systemCache;
	
	public BaseService() {
		super();
	}
	
	public String getLoggedUser() {
		return ClientContext.getInstance().getClientId();
	}
	public String getApplicableLanguage() {
		String userLang = ClientContext.getInstance().getUserLanguage();
		String result;
		if (StringUtils.isEmpty(userLang)) {
			result=systemCache.getDefaultLanguageId();
		}else {
			result = userLang.toLowerCase();
			// if request language contains a locale specific, search without it
			if (result.contains("-")) {
				result = StringUtils.substringBefore(result, "-");			
			}
			if (!systemCache.getLanguages().contains(result)) {
				result=systemCache.getDefaultLanguageId();
			}
		}
		
		return result;
	}
}