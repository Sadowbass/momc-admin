package com.momc.admin.domain.member.entity;

import com.momc.admin.domain.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity<Comment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private CommentCategory commentCategory;

    @Lob
    private String content;

    @Builder
    public Comment(Member member, CommentCategory commentCategory, String content) {
        this.member = member;
        this.commentCategory = commentCategory;
        this.content = content;
    }
}
