package com.momc.admin.domain.guild.dto;

import com.momc.admin.domain.guild.entity.GuildMemberGrade;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuildInfoDto {

    private Integer id;
    private String guildName;
    private int guildLevel;
    private int maxMemberCapacity;
    private GuildCharacterDto guildMaster;
    private List<GuildCharacterDto> guildCharacters;

    @Builder
    @QueryProjection
    public GuildInfoDto(Integer id, String guildName, int guildLevel, int maxMemberCapacity, List<GuildCharacterDto> guildCharacters) {
        this.id = id;
        this.guildName = guildName;
        this.guildLevel = guildLevel;
        this.maxMemberCapacity = maxMemberCapacity;
        this.guildCharacters = guildCharacters;
    }

    public GuildCharacterDto getGuildMaster() {
        if (guildMaster == null) {
            guildCharacters.stream()
                    .filter(guildCharacterDto -> guildCharacterDto.getGuildMemberGrade() == GuildMemberGrade.MASTER)
                    .findAny()
                    .ifPresent(guildCharacterDto -> this.guildMaster = guildCharacterDto);
        }
        return guildMaster;
    }
}
