package com.momc.admin.domain.admin.dto;

import com.momc.admin.domain.admin.entity.AdminRole;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminDto {

    private Integer id;
    private String loginId;
    private String adminName;
    private AdminRole role;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    @QueryProjection
    public AdminDto(Integer id, String loginId, String adminName, AdminRole role, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.loginId = loginId;
        this.adminName = adminName;
        this.role = role;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdminDto{");
        sb.append("id=").append(id);
        sb.append(", loginId='").append(loginId).append('\'');
        sb.append(", adminName='").append(adminName).append('\'');
        sb.append(", role=").append(role);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append('}');
        return sb.toString();
    }
}
