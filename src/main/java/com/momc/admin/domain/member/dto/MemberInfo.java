package com.momc.admin.domain.member.dto;

import com.momc.admin.domain.member.entity.MemberStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfo {

    private Integer id;
    private int expeditionLevel;
    private String mainCharacterName;
    private MemberStatus status;
    private int numberOfCharacters;
    private int numberOfComments;

    @Builder
    @QueryProjection
    public MemberInfo(Integer id, int expeditionLevel, String mainCharacterName, MemberStatus status, int numberOfCharacters, int numberOfComments) {
        this.id = id;
        this.expeditionLevel = expeditionLevel;
        this.mainCharacterName = mainCharacterName;
        this.status = status;
        this.numberOfCharacters = numberOfCharacters;
        this.numberOfComments = numberOfComments;
    }
}
