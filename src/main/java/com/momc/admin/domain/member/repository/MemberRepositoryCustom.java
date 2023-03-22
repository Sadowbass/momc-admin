package com.momc.admin.domain.member.repository;

import com.momc.admin.domain.member.dto.MemberInfo;
import com.momc.admin.domain.member.dto.MemberInfoDetail;
import com.momc.admin.utils.PageObject;
import com.momc.admin.utils.PageRequest;

import java.util.Collection;

public interface MemberRepositoryCustom {

    PageObject<MemberInfo> findAllJoinedMembers(PageRequest pageRequest);

    PageObject<MemberInfo> findAllLeftMembers(PageRequest pageRequest);

    PageObject<MemberInfo> findMembersByIdIn(PageRequest pageRequest, Collection<Integer> ids);

    MemberInfoDetail findMemberInfoDetailByMemberId(Integer memberId);
}
