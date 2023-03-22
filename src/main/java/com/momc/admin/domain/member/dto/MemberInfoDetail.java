package com.momc.admin.domain.member.dto;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.member.entity.MemberStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemberInfoDetail {

    private Integer id;
    private int expeditionLevel;
    private String mainCharacterName;
    private MemberStatus status;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private List<CharacterDetailData> characters;
    private List<CommentDto> comments;

    @Builder
    @QueryProjection
    public MemberInfoDetail(Integer id, int expeditionLevel, String mainCharacterName, MemberStatus status, LocalDate joinDate, LocalDate leaveDate, List<CommentDto> comments) {
        this.id = id;
        this.expeditionLevel = expeditionLevel;
        this.mainCharacterName = mainCharacterName;
        this.status = status;
        this.joinDate = joinDate;
        this.leaveDate = leaveDate;
        this.characters = new ArrayList<>();
        this.comments = comments != null ? comments : new ArrayList<>();
    }
}
