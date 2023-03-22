package com.momc.admin.domain.admin.entity;

import lombok.Getter;

@Getter
public enum AdminRole {

    DEVELOPER("개발자", 0), MASTER("길드마스터", 1), ADMIN("운영진", 2);

    private final String koreanName;
    private final int priority;

    AdminRole(String koreanName, int priority) {
        this.koreanName = koreanName;
        this.priority = priority;
    }

    public boolean canModify(AdminRole editor) {
        return editor.getPriority() <= this.getPriority();
    }
}
