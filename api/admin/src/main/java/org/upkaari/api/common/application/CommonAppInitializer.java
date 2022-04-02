package org.upkaari.api.common.application;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.upkaari.api.common.cache.EntityReferenceCache;

@ApplicationScoped
public class CommonAppInitializer {

	@Inject
	private EntityReferenceCache entityRefCahce;
	
	public void onStart() {
		entityRefCahce.init();
	}
	public void onStop() {
		
	}
}
