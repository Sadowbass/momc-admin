package com.momc.admin.domain.member.dto;

import com.momc.admin.domain.member.entity.CommentCategory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto implements Comparable<CommentDto>{

    private Integer id;
    private Integer memberId;
    private CommentCategory commentCategory;
    private String content;
    private String author;
    private LocalDateTime createdDate;

    @Builder
    @QueryProjection
    public CommentDto(Integer id, Integer memberId, CommentCategory commentCategory, String content, String author, LocalDateTime createdDate) {
        this.id = id;
        this.memberId = memberId;
        this.commentCategory = commentCategory;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
    }

    @Override
    public int compareTo(CommentDto o) {
        return this.commentCategory.getPriority() - o.commentCategory.getPriority();
    }
}
