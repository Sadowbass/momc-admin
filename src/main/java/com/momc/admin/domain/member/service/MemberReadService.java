package com.momc.admin.domain.member.service;

import com.momc.admin.domain.member.dto.MemberInfo;
import com.momc.admin.domain.member.dto.MemberInfoDetail;
import com.momc.admin.utils.PageObject;
import com.momc.admin.utils.PageRequest;

import java.util.List;

public interface MemberReadService {

    PageObject<MemberInfo> getAllJoinedMembers(PageRequest pageRequest);

    PageObject<MemberInfo> getAllLeftMembers(PageRequest pageRequest);

    PageObject<MemberInfo> findMembersByIds(PageRequest pageRequest, List<Integer> memberIds);

    MemberInfoDetail getMemberInfoDetail(Integer memberId);
}
