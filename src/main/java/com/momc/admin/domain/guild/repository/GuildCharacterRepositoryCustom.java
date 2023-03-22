package com.momc.admin.domain.guild.repository;

import com.momc.admin.domain.guild.entity.GuildCharacter;

import java.util.List;

public interface GuildCharacterRepositoryCustom {

    void bulkUpdate(List<GuildCharacter> guildCharacterList);

    void bulkDelete(List<GuildCharacter> guildCharacterList);
}
