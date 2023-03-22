package com.momc.admin.domain.member.service;

import java.time.LocalDate;

public interface MemberModifyService {
    
    void modifyMember(Integer memberId, Integer characterId, LocalDate joinDate);
}
