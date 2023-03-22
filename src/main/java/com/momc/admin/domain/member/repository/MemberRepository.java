package com.momc.admin.domain.member.repository;

import com.momc.admin.domain.common.repository.BaseRepository;
import com.momc.admin.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends MemberRepositoryCustom, BaseRepository<Member, Integer>, JpaRepository<Member, Integer> {

    @Override
    default Member getEntityByPkElseThrow(Integer id) {
        //TODO create new exception
        return findById(id).orElseThrow(() -> new RuntimeException(String.format("cannot find entity by pk : %s", id)));
    }

}
