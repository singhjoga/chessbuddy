package org.upkaari.api.common.services;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.upkaari.api.common.cache.EntityReferenceCache;
import org.upkaari.api.common.domain.IdentifiableEntity;
import org.upkaari.api.common.exception.ResourceNotFoundException;
import org.upkaari.api.common.repos.BaseRepository;

public class BaseEntityService<T extends IdentifiableEntity<ID>, ID extends Serializable> extends BaseService {

	protected BaseRepository<T, ID> repo;
	protected Class<T> entityClass;
	@Inject
	protected EntityReferenceCache staticCache;

	public BaseEntityService(BaseRepository<T, ID> repo, Class<T> entityClass, Class<ID> idClass) {
		super();
		this.repo=repo;
		this.entityClass=entityClass;
	}

	public T getById(ID id) {
		return getById(id, false);
	}

	public T getById(ID id, boolean optional) {
		T obj = repo.findById (id);
		if (obj == null && !optional) {
			throw new ResourceNotFoundException(entityClass.getSimpleName(), id.toString());
		}
		return obj;
	}

	public T findById(ID id) {
		T obj = repo.findById(id);
	
		return obj;
	}

	public List<T> findAll() {
		return repo.listAll();
	}
}