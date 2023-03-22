package com.momc.admin.domain.guild.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GuildCreateResult {

    private Integer id;
    private String guildName;
    private int guildLevel;
    private int maxMemberCapacity;
}
