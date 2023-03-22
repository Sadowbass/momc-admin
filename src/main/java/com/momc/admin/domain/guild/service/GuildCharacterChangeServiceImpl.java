package com.momc.admin.domain.guild.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.characters.repository.CharactersRepository;
import com.momc.admin.domain.guild.entity.Guild;
import com.momc.admin.domain.guild.entity.GuildMemberGrade;
import com.momc.admin.domain.guild.repository.GuildCharacterRepository;
import com.momc.admin.domain.guild.repository.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuildCharacterChangeServiceImpl implements GuildCharacterChangeService {

    private final GuildRepository guildRepository;
    private final GuildCharacterRepository guildCharacterRepository;
    private final CharactersRepository charactersRepository;

    @Override
    public void joinGuild(Integer memberId, List<CharacterDetailData> characterDetailDataList) {
        Map<String, Guild> guildMap = guildRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Guild::getGuildName, Function.identity()));

        Map<Integer, Characters> charactersMap = charactersRepository.findAllById(findAllGuildCharacters(guildMap, characterDetailDataList))
                .stream()
                .collect(Collectors.toMap(Characters::getId, Function.identity()));

        saveAllGuildCharacters(characterDetailDataList, guildMap, charactersMap);
    }

    private List<Integer> findAllGuildCharacters(Map<String, Guild> guildMap, List<CharacterDetailData> characterDetailDataList) {
        return characterDetailDataList.stream()
                .filter(each -> guildMap.containsKey(each.getGuildName()))
                .map(CharacterDetailData::getCharacterId)
                .collect(Collectors.toList());
    }

    private void saveAllGuildCharacters(List<CharacterDetailData> characterDetailDataList, Map<String, Guild> guildMap, Map<Integer, Characters> charactersMap) {
        characterDetailDataList.stream()
                .filter(dto -> charactersMap.containsKey(dto.getCharacterId()))
                .map(dto -> {
                    Characters characters = charactersMap.get(dto.getCharacterId());
                    GuildMemberGrade guildMemberGrade = GuildMemberGrade.getGradeByKoreanName(dto.getGuildMemberGrade());

                    Guild guild = guildMap.get(dto.getGuildName());
                    return guild.addNewGuildCharacter(characters, guildMemberGrade);
                })
                .forEach(guildCharacterRepository::save);
    }
}
