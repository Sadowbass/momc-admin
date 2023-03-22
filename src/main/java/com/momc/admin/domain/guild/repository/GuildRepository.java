package com.momc.admin.domain.guild.repository;

import com.momc.admin.domain.common.repository.BaseRepository;
import com.momc.admin.domain.guild.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildRepository extends GuildRepositoryCustom, BaseRepository<Guild, Integer>, JpaRepository<Guild, Integer> {

    boolean existsByGuildName(String guildName);

    @Override
    default Guild getEntityByPkElseThrow(Integer guildId) {
        //TODO create exception
        return findById(guildId).orElseThrow(() -> new RuntimeException("no guild find"));
    }
}
