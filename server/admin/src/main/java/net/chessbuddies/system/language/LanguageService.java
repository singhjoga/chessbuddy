package net.chessbuddies.system.language;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.technovator.api.common.services.BaseEntityService;

@Singleton
public class LanguageService extends BaseEntityService<Language, String>{
	private LanguageRepo repo;
	@Inject
	public LanguageService(LanguageRepo repo) {
		super(repo, Language.class, String.class);
		this.repo=repo;
	}
	public List<Language> findAllByIsDisabled(Boolean isDisabled){
		return repo.findAllByIsDisabled(isDisabled);
	}
}
