package com.momc.admin.infra.config.security.auth;

import com.momc.admin.domain.admin.entity.Admin;
import com.momc.admin.domain.admin.entity.AdminRole;
import com.momc.admin.domain.admin.entity.AdminStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCredential {

    private Integer id;
    private String loginId;
    private String adminName;
    private String password;
    private AdminRole role;
    private AdminStatus status;

    public AdminCredential(Admin admin) {
        this.id = admin.getId();
        this.loginId = admin.getLoginId();
        this.adminName = admin.getAdminName();
        this.password = admin.getPassword();
        this.role = admin.getRole();
        this.status = admin.getStatus();
    }
}
