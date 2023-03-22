package com.momc.admin.domain.common.repository;

public interface BaseRepository<T, ID> {

    T getEntityByPkElseThrow(ID id);
}
