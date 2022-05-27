package net.chessbuddies.system.cache;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.technovator.api.common.cache.SystemCache;
import com.technovator.api.common.constants.Constants;

import lombok.extern.slf4j.Slf4j;
import net.chessbuddies.system.language.LanguageService;

@ApplicationScoped
@Slf4j
public class LocalSystemCache implements SystemCache{
	private static final Logger LOG = LoggerFactory.getLogger(LocalSystemCache.class);
	@Inject
	private LanguageService langService;
	private Set<String> languages;

	public Set<String> getLanguages() {
		return languages;
	}


	private void loadLanguages() {
		languages = new HashSet<>();
		langService.findAllByIsDisabled(false).stream().forEach(e->languages.add(e.getId()));
	}
	@Override
	public String getDefaultLanguageId() {
		return Constants.ENGLISH_LANGUAGE_CODE;
	}
	@Override
	public void init() {
		loadLanguages();
		log.info("System cache initialized");
	}
	
}
