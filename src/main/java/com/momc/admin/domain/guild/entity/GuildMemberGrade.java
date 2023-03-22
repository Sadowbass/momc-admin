package com.momc.admin.domain.guild.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GuildMemberGrade {
    MASTER("길드장", 0),
    VICE("부길드장", 1),
    ADMIN("임원", 2),
    MEMBER("일반 길드원", 3);

    private final String gradeKoreanName;
    private final int priority;

    GuildMemberGrade(String gradeKoreanName, int priority) {
        this.gradeKoreanName = gradeKoreanName;
        this.priority = priority;
    }

    public static GuildMemberGrade getGradeByKoreanName(String koreanName) {
        return Arrays.stream(GuildMemberGrade.values())
                .filter(grade -> grade.getGradeKoreanName().equals(koreanName))
                .findFirst()
                .orElse(GuildMemberGrade.MEMBER);
    }

    public boolean isSame(String value) {
        return this.name().equalsIgnoreCase(value) || this.getGradeKoreanName().equals(value);
    }
}
