package com.momc.admin.domain.guild.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuildCreateForm {

    private String guildName;
    private int guildLevel;
}
