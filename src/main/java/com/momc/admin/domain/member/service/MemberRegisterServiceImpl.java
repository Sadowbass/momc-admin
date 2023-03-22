package com.momc.admin.domain.member.service;

import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.characters.repository.CharactersRepository;
import com.momc.admin.domain.member.entity.Member;
import com.momc.admin.domain.member.entity.MemberStatus;
import com.momc.admin.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberRegisterServiceImpl implements MemberRegisterService {

    private final MemberRepository memberRepository;
    private final CharactersRepository charactersRepository;

    @Override
    public Integer joinMember(LocalDate joinDate) {
        Member savedMember = memberRepository.save(new Member(joinDate));
        return savedMember.getId();
    }

    @Override
    public void setDefaultJoinData(Integer memberId, Integer characterId, int expeditionLevel) {
        Member member = memberRepository.getEntityByPkElseThrow(memberId);
        Characters mainCharacter = charactersRepository.findById(characterId).orElseThrow(RuntimeException::new);
        member.changeMainCharacter(mainCharacter);
        member.changeExpeditionLevel(expeditionLevel);
    }

    @Override
    public void leaveMember(Integer memberId, MemberStatus reason, LocalDate leaveDate) {
        Member member = memberRepository.getEntityByPkElseThrow(memberId);
        member.leaveMember(reason, leaveDate);
    }
}
