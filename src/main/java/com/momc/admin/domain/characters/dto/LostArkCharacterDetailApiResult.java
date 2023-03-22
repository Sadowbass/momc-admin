package com.momc.admin.domain.characters.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class LostArkCharacterDetailApiResult {

    private int expeditionLevel;
    private String guildMemberGrade;
    private String guildName;
    private String characterName;
    private int characterLevel;
    private String characterClassName;
    private String itemAvgLevel;

    public double getItemAvgLevelAsDouble() {
        return Double.parseDouble(this.itemAvgLevel.replace(",", ""));
    }
}
