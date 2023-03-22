package com.momc.admin.application.service;

import com.momc.admin.domain.admin.service.AdminRegisterService;
import com.momc.admin.infra.config.security.auth.AdminCredential;
import com.momc.admin.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAppService {

    private final AdminRegisterService adminRegisterService;

    @Transactional
    public void deleteAdmin(Integer adminId) {
        AdminCredential adminCredential = SecurityUtils.getAdminCredential();
        adminRegisterService.deleteAdmin(adminId, adminCredential.getRole());
        SecurityUtils.doLogoutIfLoggedIn(adminId);
    }
}
