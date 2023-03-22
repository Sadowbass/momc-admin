package com.momc.admin.domain.guild.service;

import com.momc.admin.domain.guild.dto.GuildInfoDto;

import java.util.List;

public interface GuildReadService {

    List<GuildInfoDto> getAllGuildInfo();

    GuildInfoDto getGuildInfo(Integer guildId);

    GuildInfoDto getGuildInfoForTransaction(Integer guildId);
}
