package com.momc.admin.domain.characters.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.characters.exception.DuplicateCharacterException;
import com.momc.admin.domain.characters.repository.CharactersRepository;
import com.momc.admin.domain.member.entity.Member;
import com.momc.admin.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterSaveServiceImpl implements CharacterSaveService {

    private final CharactersRepository charactersRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<CharacterDetailData> saveAllCharacters(Integer memberId, List<CharacterDetailData> characterDetailDataList) {
        Member member = memberRepository.getEntityByPkElseThrow(memberId);
        validDuplicateCharacters(characterDetailDataList);

        List<Characters> characters = characterDetailDataList.stream()
                .map(dto -> convertToCharacters(member, dto))
                .collect(Collectors.toList());
        charactersRepository.saveAllBulkInsert(characters);

        List<Characters> savedCharacters = charactersRepository.findAllByMemberId(memberId);
        setIdToDto(savedCharacters, characterDetailDataList);

        return characterDetailDataList;
    }

    private void validDuplicateCharacters(List<CharacterDetailData> dtoList) {
        List<String> collect = dtoList.stream()
                .map(CharacterDetailData::getCharacterName)
                .collect(Collectors.toList());

        List<Characters> savedCharacters = charactersRepository.findByCharacterNameIn(collect);
        if (!savedCharacters.isEmpty()) {
            List<String> duplicateCharacters = savedCharacters.stream()
                    .map(Characters::getCharacterName)
                    .collect(Collectors.toList());
            throw new DuplicateCharacterException(duplicateCharacters);
        }
    }

    private Characters convertToCharacters(Member member, CharacterDetailData dto) {
        return Characters.builder()
                .member(member)
                .characterName(dto.getCharacterName())
                .className(dto.getClassName())
                .combatLevel(dto.getCombatLevel())
                .itemLevel(dto.getItemLevel())
                .build()
                .setDefaultDateForBulkInsert();
    }

    @Override
    public Map<String, CharacterDetailData> updateAllCharacters(Map<String, CharacterDetailData> detailDataMap) {
        List<Characters> characters = charactersRepository.findAllByCharacterNameIn(detailDataMap.keySet());
        setIdToDto(characters, detailDataMap.values());

        characters.stream()
                .filter(c -> {
                    CharacterDetailData v = detailDataMap.get(c.getCharacterName());
                    return c.hasAnyChange(v.getClassName(), v.getCombatLevel(), v.getItemLevel());
                })
                .map(c -> {
                    CharacterDetailData v = detailDataMap.get(c.getCharacterName());
                    c.updateCharacter(v.getClassName(), v.getCombatLevel(), v.getItemLevel());
                    return c;
                })
                .collect(Collectors.toList());

        return detailDataMap;
    }

    private void setIdToDto(List<Characters> characters, Collection<CharacterDetailData> dtoList) {
        Map<String, Integer> collect = characters.stream()
                .collect(Collectors.toMap(Characters::getCharacterName, Characters::getId));

        dtoList.stream()
                .filter(dto -> collect.containsKey(dto.getCharacterName()))
                .forEach(dto -> dto.setCharacterId(collect.get(dto.getCharacterName())));
    }
}
