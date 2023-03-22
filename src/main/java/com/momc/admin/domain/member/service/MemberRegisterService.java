package com.momc.admin.domain.member.service;

import com.momc.admin.domain.member.entity.MemberStatus;

import java.time.LocalDate;

public interface MemberRegisterService {

    Integer joinMember(LocalDate joinDate);

    void setDefaultJoinData(Integer memberId, Integer characterId, int expeditionLevel);

    void leaveMember(Integer memberId, MemberStatus reason, LocalDate leaveDate);
}
