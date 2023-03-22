package com.momc.admin.domain.member.entity;

import lombok.Getter;

@Getter
public enum MemberStatus {
    JOIN("가입"), LEAVE("탈퇴"), BANNED("강퇴");

    private final String korean;

    MemberStatus(String korean) {
        this.korean = korean;
    }
}
