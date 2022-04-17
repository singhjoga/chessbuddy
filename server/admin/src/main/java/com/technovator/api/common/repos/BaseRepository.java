package com.technovator.api.common.repos;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public interface BaseRepository<T, ID> extends PanacheRepositoryBase<T, ID> {

}
