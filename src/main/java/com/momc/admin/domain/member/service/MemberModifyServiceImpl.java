package com.momc.admin.domain.member.service;

import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.characters.repository.CharactersRepository;
import com.momc.admin.domain.member.entity.Member;
import com.momc.admin.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberModifyServiceImpl implements MemberModifyService {

    private final MemberRepository memberRepository;
    private final CharactersRepository charactersRepository;

    @Override
    public void modifyMember(Integer memberId, Integer characterId, LocalDate joinDate) {
        Member member = memberRepository.getEntityByPkElseThrow(memberId);
        if (characterId != null) {
            Characters newMainCharacter = charactersRepository.getEntityByPkElseThrow(characterId);
            member.changeMainCharacter(newMainCharacter);
        }
        if (joinDate != null) {
            member.changeJoinDate(joinDate);
        }
    }
}
