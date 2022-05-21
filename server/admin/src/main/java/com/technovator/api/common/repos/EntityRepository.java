package com.technovator.api.common.repos;

import java.io.Serializable;
import java.util.List;

public interface EntityRepository<T, ID extends Serializable> extends BaseRepository<T, ID>{
	default List<T> findAllByIsDisabled(Boolean isDisabled) {
		return find("isDisabled", isDisabled).list();
	}
}
