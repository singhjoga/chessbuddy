package com.technovator.api.common.cache;

import java.util.Set;

public interface SystemCache {
	public Set<String> getLanguages();
	public String getDefaultLanguageId();
	public void init();
}