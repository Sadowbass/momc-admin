package com.momc.admin.domain.member.entity;

import lombok.Getter;

@Getter
public enum CommentCategory {
    COMMENT(1), REPORT(1), LEAVE(0);

    private final int priority;

    CommentCategory(int priority) {
        this.priority = priority;
    }
}
