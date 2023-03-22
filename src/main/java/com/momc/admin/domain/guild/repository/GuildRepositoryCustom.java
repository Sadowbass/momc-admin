package com.momc.admin.domain.guild.repository;

import com.momc.admin.domain.guild.dto.GuildInfoDto;
import com.momc.admin.domain.guild.entity.Guild;

import java.util.List;
import java.util.Optional;

public interface GuildRepositoryCustom {

    List<GuildInfoDto> findAllGuildInfoDto();

    GuildInfoDto findGuildInfoDto(Integer guildId);

    Optional<Guild> findByIdFetchAll(Integer guildId);
}
