package com.momc.admin.domain.admin.service;

import com.momc.admin.application.controller.admin.form.AdminRegisterForm;
import com.momc.admin.domain.admin.entity.Admin;
import com.momc.admin.domain.admin.entity.AdminRole;
import com.momc.admin.domain.admin.entity.AdminStatus;
import com.momc.admin.domain.admin.exception.DuplicateAdminIdException;
import com.momc.admin.infra.config.security.auth.exception.IllegalAuthorityException;
import com.momc.admin.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AdminRegisterServiceImpl implements AdminRegisterService {

    private final AdminRepository adminRepository;
    private final PasswordEncodeService encodeService;

    @Override
    public void register(AdminRegisterForm form) {
        checkDuplication(form.getLoginId(), form.getAdminName());
        form.setPassword(encodeService.encode(form.getPassword()));

        Admin newAdmin = Admin.builder()
                .loginId(form.getLoginId())
                .adminName(form.getAdminName())
                .password(form.getPassword())
                .build();

        adminRepository.save(newAdmin);
    }

    private void checkDuplication(String loginId, String adminName) {
        if (adminRepository.existsByLoginIdOrAdminName(loginId, adminName)) {
            throw new DuplicateAdminIdException("로그인 id 혹은 이름 중복");
        }
    }

    @Override
    @Transactional
    public void approveRegister(Integer id) {
        Admin admin = adminRepository.getEntityByPkElseThrow(id);
        admin.approve();
    }

    @Override
    @Transactional
    public void rejectRegister(Integer id) {
        Admin admin = adminRepository.getEntityByPkElseThrow(id);
        if (admin.getStatus() == AdminStatus.WAIT) {
            adminRepository.delete(admin);
        }
    }

    @Override
    public void deleteAdmin(Integer id, AdminRole editorRole) {
        Admin admin = adminRepository.getEntityByPkElseThrow(id);
        if (admin.getRole().canModify(editorRole)) {
            admin.deleteAdmin();
            return;
        }
        throw new IllegalAuthorityException("권한이 부족합니다");
    }
}
