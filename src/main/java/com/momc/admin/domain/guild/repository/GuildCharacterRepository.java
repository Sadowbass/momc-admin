package com.momc.admin.domain.guild.repository;

import com.momc.admin.domain.common.repository.BaseRepository;
import com.momc.admin.domain.guild.entity.GuildCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GuildCharacterRepository extends GuildCharacterRepositoryCustom, BaseRepository<GuildCharacter, Integer>, JpaRepository<GuildCharacter, Integer> {

    @Query("select gc from GuildCharacter gc join fetch Characters c where c.id = :characterId")
    Optional<GuildCharacter> findByCharactersId(Integer characterId);

    @Override
    default GuildCharacter getEntityByPkElseThrow(Integer guildCharacterId) {
        //TODO create exception
        return findById(guildCharacterId).orElseThrow(() -> new RuntimeException("no guildCharacter find"));
    }
}
