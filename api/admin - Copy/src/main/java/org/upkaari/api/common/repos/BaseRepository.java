package org.upkaari.api.common.repos;

import java.io.Serializable;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public interface BaseRepository<T, ID extends Serializable> extends PanacheRepositoryBase<T, ID> {

}
