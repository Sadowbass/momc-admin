package com.momc.admin.domain.guild.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.guild.dto.GuildCreateResult;
import com.momc.admin.domain.guild.entity.Guild;
import com.momc.admin.domain.guild.entity.GuildCharacter;
import com.momc.admin.domain.guild.entity.GuildMemberGrade;
import com.momc.admin.domain.guild.repository.GuildCharacterRepository;
import com.momc.admin.domain.guild.repository.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuildCommandServiceImpl implements GuildCommandService {

    private final GuildRepository guildRepository;
    private final GuildCharacterRepository guildCharacterRepository;
    private final EntityManager em;

    @Override
    public GuildCreateResult createNewGuild(String guildName, int guildLevel) {
        if (guildRepository.existsByGuildName(guildName)) {
            //TODO create new exception
            throw new RuntimeException("duplicate guild name");
        }

        Guild newGuild = guildRepository.save(new Guild(guildName, guildLevel));
        return GuildCreateResult.builder()
                .id(newGuild.getId())
                .guildName(newGuild.getGuildName())
                .guildLevel(newGuild.getGuildLevel())
                .maxMemberCapacity(newGuild.getMaxMemberCapacity())
                .build();
    }

    @Override
    public GuildCreateResult changeGuildData(Integer guildId, String guildName, int guildLevel) {
        Guild guild = guildRepository.getEntityByPkElseThrow(guildId);
        guild.changeGuildName(guildName);
        guild.changeGuildLevel(guildLevel);

        return GuildCreateResult.builder()
                .id(guild.getId())
                .guildName(guild.getGuildName())
                .guildLevel(guild.getGuildLevel())
                .maxMemberCapacity(guild.getMaxMemberCapacity())
                .build();
    }

    @Override
    public void updateGuildCharacters(Integer guildId, Map<String, CharacterDetailData> detailDataMap) {
        Guild guild = guildRepository.findById(guildId).orElseThrow();
        guild.getGuildCharacters().forEach(em::detach);

        List<GuildCharacter> updateTargets = createUpdateTarget(detailDataMap, guild);
        guildCharacterRepository.bulkUpdate(updateTargets);

        List<GuildCharacter> deleteTarget = createDeleteTarget(detailDataMap, guild);
        guildCharacterRepository.bulkDelete(deleteTarget);
    }

    private List<GuildCharacter> createUpdateTarget(Map<String, CharacterDetailData> detailDataMap, Guild guild) {
        List<GuildCharacter> guildCharacters = guild.getGuildCharacters();
        return guildCharacters.stream()
                .filter(gc -> {
                    CharacterDetailData detailData = detailDataMap.get(gc.getCharacters().getCharacterName());
                    return isSameGuild(guild.getGuildName(), detailData.getGuildName())
                            && !gc.getGuildMemberGrade().isSame(detailData.getGuildMemberGrade());
                })
                .map(gc -> {
                    String guildMemberGrade = detailDataMap.get(gc.getCharacters().getCharacterName()).getGuildMemberGrade();
                    GuildMemberGrade changedGuildGrade = GuildMemberGrade.getGradeByKoreanName(guildMemberGrade);
                    gc.changeGuildMemberGrade(changedGuildGrade);
                    return gc;
                })
                .collect(Collectors.toList());
    }

    private List<GuildCharacter> createDeleteTarget(Map<String, CharacterDetailData> detailDataMap, Guild guild) {
        List<GuildCharacter> guildCharacters = guild.getGuildCharacters();
        return guildCharacters.stream()
                .filter(gc -> {
                    CharacterDetailData detailData = detailDataMap.get(gc.getCharacters().getCharacterName());
                    return !isSameGuild(guild.getGuildName(), detailData.getGuildName());
                })
                .collect(Collectors.toList());
    }

    private boolean isSameGuild(String guildName, String charactersGuildName) {
        return guildName.equals(charactersGuildName);
    }
}
