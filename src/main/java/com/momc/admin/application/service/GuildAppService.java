package com.momc.admin.application.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.characters.infra.CharacterLostArkApiClient;
import com.momc.admin.domain.characters.service.CharacterSaveService;
import com.momc.admin.domain.guild.dto.GuildCharacterDto;
import com.momc.admin.domain.guild.dto.GuildCreateResult;
import com.momc.admin.domain.guild.dto.GuildInfoDto;
import com.momc.admin.domain.guild.service.GuildCommandService;
import com.momc.admin.domain.guild.service.GuildReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuildAppService {

    private final GuildCommandService guildCommandService;
    private final GuildReadService guildReadService;
    private final CharacterLostArkApiClient lostArkApiClient;
    private final CharacterSaveService characterSaveService;

    @Transactional
    public GuildCreateResult createNewGuild(String guildName, int guildLevel) {
        return guildCommandService.createNewGuild(guildName, guildLevel);
    }

    @Transactional
    public GuildCreateResult changeGuildData(Integer guildId, String guildName, int guildLevel) {
        return guildCommandService.changeGuildData(guildId, guildName, guildLevel);
    }

    @Transactional
    public void refresh(Integer guildId) {
        GuildInfoDto guildInfo = guildReadService.getGuildInfoForTransaction(guildId);
        List<String> characterNames = guildInfo.getGuildCharacters()
                .stream()
                .map(GuildCharacterDto::getCharacterName)
                .collect(Collectors.toList());

        Map<String, CharacterDetailData> detailDataMap = lostArkApiClient.getAllCharacterDetailData(characterNames, false)
                .stream()
                .collect(Collectors.toMap(CharacterDetailData::getCharacterName, Function.identity()));

        guildCommandService.updateGuildCharacters(guildId, detailDataMap);
        characterSaveService.updateAllCharacters(detailDataMap);
    }
}
