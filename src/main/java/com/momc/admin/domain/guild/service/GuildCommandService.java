package com.momc.admin.domain.guild.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.guild.dto.GuildCreateResult;

import java.util.Map;

public interface GuildCommandService {

    GuildCreateResult createNewGuild(String guildName, int guildLevel);

    GuildCreateResult changeGuildData(Integer guildId, String guildName, int guildLevel);

    void updateGuildCharacters(Integer guildId, Map<String, CharacterDetailData> detailDataMap);
}
