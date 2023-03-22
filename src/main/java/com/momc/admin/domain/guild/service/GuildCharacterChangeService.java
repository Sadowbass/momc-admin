package com.momc.admin.domain.guild.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.guild.entity.GuildCharacter;

import java.util.List;

public interface GuildCharacterChangeService {

    void joinGuild(Integer memberId, List<CharacterDetailData> characterDetailDataList);
}
