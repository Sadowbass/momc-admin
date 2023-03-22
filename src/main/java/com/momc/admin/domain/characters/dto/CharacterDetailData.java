package com.momc.admin.domain.characters.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CharacterDetailData {

    private Integer memberId;
    private Integer characterId;
    private int expeditionLevel;
    private String characterName;
    private String className;
    private int combatLevel;
    private double itemLevel;
    private String guildName;
    private String guildMemberGrade;

    @QueryProjection
    public CharacterDetailData(Integer memberId, Integer characterId, int expeditionLevel, String characterName, String className, int combatLevel, double itemLevel, String guildName, String guildMemberGrade) {
        this.memberId = memberId;
        this.characterId = characterId;
        this.expeditionLevel = expeditionLevel;
        this.characterName = characterName;
        this.className = className;
        this.combatLevel = combatLevel;
        this.itemLevel = itemLevel;
        this.guildName = guildName;
        this.guildMemberGrade = guildMemberGrade;
    }
}
