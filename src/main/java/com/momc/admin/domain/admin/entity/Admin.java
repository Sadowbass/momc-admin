package com.momc.admin.domain.admin.entity;

import com.momc.admin.domain.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity<Admin> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String loginId;
    private String adminName;
    private String password;

    @Enumerated(EnumType.STRING)
    private AdminRole role;

    @Enumerated(EnumType.STRING)
    private AdminStatus status;

    @Builder
    public Admin(String loginId, String adminName, String password) {
        this.loginId = loginId;
        this.adminName = adminName;
        this.password = password;
        this.role = AdminRole.ADMIN;
        this.status = AdminStatus.WAIT;
    }

    public void approve() {
        this.status = AdminStatus.APPROVAL;
    }

    public void changeAdminRole(AdminRole adminRole) {
        this.role = adminRole;
    }

    public void deleteAdmin() {
        this.status = AdminStatus.DELETED;
    }
}
