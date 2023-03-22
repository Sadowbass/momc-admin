package com.momc.admin.domain.admin.repository;

import com.momc.admin.domain.admin.entity.Admin;
import com.momc.admin.domain.common.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends AdminRepositoryCustom, BaseRepository<Admin, Integer>, JpaRepository<Admin, Integer> {

    boolean existsByLoginIdOrAdminName(String loginId, String adminName);

    Optional<Admin> findByLoginId(String loginId);

    @Override
    default Admin getEntityByPkElseThrow(Integer id) {
        //TODO create new exception
        return findById(id).orElseThrow(() -> new RuntimeException(String.format("cannot find entity by pk : %s", id)));
    }
}
