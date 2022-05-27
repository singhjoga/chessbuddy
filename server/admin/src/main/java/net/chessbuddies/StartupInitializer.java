package net.chessbuddies;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.technovator.api.common.application.AppInitializer;
import com.technovator.api.common.translation.TranslationService;

import net.chessbuddies.system.cache.LocalSystemCache;

@ApplicationScoped
public class StartupInitializer implements AppInitializer{
	@Inject
	private LocalSystemCache systemCache;
	@Inject
	private TranslationService translationService;
	
	@Override
	public void onStart() {
		systemCache.init();
		translationService.init();
	}

	@Override
	public void onStop() {
		
	}

}
