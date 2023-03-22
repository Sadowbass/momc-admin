package com.momc.admin.domain.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WaitingAdminDto {

    private Integer id;
    private String loginId;
    private String adminName;
    private LocalDateTime createdDate;

    @Builder
    @QueryProjection
    public WaitingAdminDto(Integer id, String loginId, String adminName, LocalDateTime createdDate) {
        this.id = id;
        this.loginId = loginId;
        this.adminName = adminName;
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WaitingAdminDto{");
        sb.append("id=").append(id);
        sb.append(", loginId='").append(loginId).append('\'');
        sb.append(", adminName='").append(adminName).append('\'');
        sb.append(", createdDate=").append(createdDate);
        sb.append('}');
        return sb.toString();
    }
}
