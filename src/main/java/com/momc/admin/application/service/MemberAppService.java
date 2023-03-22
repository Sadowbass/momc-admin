package com.momc.admin.application.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.characters.infra.CharacterLostArkApiClient;
import com.momc.admin.domain.characters.service.CharacterReadService;
import com.momc.admin.domain.characters.service.CharacterSaveService;
import com.momc.admin.domain.guild.service.GuildCharacterChangeService;
import com.momc.admin.domain.member.dto.MemberInfo;
import com.momc.admin.domain.member.dto.MemberInfoDetail;
import com.momc.admin.domain.member.dto.MemberLeaveForm;
import com.momc.admin.domain.member.service.CommentService;
import com.momc.admin.domain.member.service.MemberModifyService;
import com.momc.admin.domain.member.service.MemberReadService;
import com.momc.admin.domain.member.service.MemberRegisterService;
import com.momc.admin.utils.PageObject;
import com.momc.admin.utils.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAppService {

    private final MemberRegisterService memberRegisterService;
    private final MemberReadService memberReadService;
    private final MemberModifyService memberModifyService;
    private final CharacterSaveService characterSaveService;
    private final CharacterReadService characterReadService;
    private final CharacterLostArkApiClient lostArkApiClient;
    private final GuildCharacterChangeService guildCharacterChangeService;
    private final CommentService commentService;

    @Transactional
    public void registerNewMember(String characterName, LocalDate joinDate) {
        Integer memberId = memberRegisterService.joinMember(joinDate);

        List<CharacterDetailData> characterDetailDataList = lostArkApiClient.getAllCharacterDetailDataByMainCharacter(characterName, false);
        characterSaveService.saveAllCharacters(memberId, characterDetailDataList);

        CharacterDetailData mainCharacter = findMainCharacter(characterName, characterDetailDataList);

        memberRegisterService.setDefaultJoinData(
                memberId,
                mainCharacter.getCharacterId(),
                mainCharacter.getExpeditionLevel()
        );

        guildCharacterChangeService.joinGuild(memberId, characterDetailDataList);
    }

    private CharacterDetailData findMainCharacter(String characterName, List<CharacterDetailData> characterDetailDataList) {
        return characterDetailDataList
                .stream()
                .filter(character -> character.getCharacterName().equalsIgnoreCase(characterName))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void leaveMember(Integer memberId, MemberLeaveForm form) {
        memberRegisterService.leaveMember(memberId, form.getReason(), form.getLeaveDate());
        commentService.writeNewComment(memberId, form.getCommentType(), form.getComment());
    }

    @Transactional
    public void modifyMember(Integer memberId, Integer characterId, LocalDate joinDate) {
        memberModifyService.modifyMember(memberId, characterId, joinDate);
    }

    public PageObject<MemberInfo> findMembersByCharacterName(PageRequest pageRequest, String characterName) {
        List<Integer> memberIds = characterReadService.getMemberIdsByCharacterNameLike(characterName);
        return memberReadService.findMembersByIds(pageRequest, memberIds);
    }

    public PageObject<MemberInfo> getAllJoinedMember(PageRequest pageRequest) {
        return memberReadService.getAllJoinedMembers(pageRequest);
    }

    public PageObject<MemberInfo> getAllLeftMembers(PageRequest pageRequest) {
        return memberReadService.getAllLeftMembers(pageRequest);
    }

    public MemberInfoDetail getMemberDetail(Integer memberId) {
        MemberInfoDetail memberDetail = memberReadService.getMemberInfoDetail(memberId);

        List<CharacterDetailData> characters = characterReadService.getMembersAllCharacters(memberId);
        characters.sort((o1, o2) -> (int) (o2.getItemLevel() - o1.getItemLevel()));
        memberDetail.setCharacters(characters);

        return memberDetail;
    }
}
