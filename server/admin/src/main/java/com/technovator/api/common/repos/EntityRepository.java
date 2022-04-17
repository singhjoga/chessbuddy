package com.technovator.api.common.repos;

import java.io.Serializable;

public interface EntityRepository<T, ID extends Serializable> extends BaseRepository<T, ID>{

}
