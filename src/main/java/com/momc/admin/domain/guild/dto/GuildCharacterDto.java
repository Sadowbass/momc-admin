package com.momc.admin.domain.guild.dto;

import com.momc.admin.domain.guild.entity.GuildMemberGrade;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuildCharacterDto {

    private Integer id;
    private Integer memberId;
    private Integer characterId;
    private String mainCharacterName;
    private String characterName;
    private Double itemLevel;
    private String className;
    private GuildMemberGrade guildMemberGrade;

    @Builder
    @QueryProjection
    public GuildCharacterDto(Integer id, Integer memberId, Integer characterId, String mainCharacterName, String characterName, Double itemLevel, String className, GuildMemberGrade guildMemberGrade) {
        this.id = id;
        this.memberId = memberId;
        this.mainCharacterName = mainCharacterName;
        this.characterName = characterName;
        this.itemLevel = itemLevel;
        this.className = className;
        this.guildMemberGrade = guildMemberGrade;
    }

    public static int comparator(GuildCharacterDto o1, GuildCharacterDto o2) {
        int compareGrade = o1.getGuildMemberGrade().getPriority() - o2.getGuildMemberGrade().getPriority();
        if (compareGrade != 0) {
            return compareGrade;
        }

        return (int) (o2.getItemLevel() - o1.getItemLevel());
    }
}