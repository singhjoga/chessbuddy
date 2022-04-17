package com.technovator.api.common.cache;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EntityReferenceCache {
	private static final Logger LOG = LoggerFactory.getLogger(EntityReferenceCache.class);

	private EntityCacheReference entityReferenceCache;
	public void init() {
		entityReferenceCache = new EntityCacheReference();
		entityReferenceCache.init();
	}

	public EntityCacheReference getEntityReferenceCache() {
		return entityReferenceCache;
	}

}
