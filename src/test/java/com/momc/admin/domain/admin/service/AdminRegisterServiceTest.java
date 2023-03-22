package com.momc.admin.domain.admin.service;

import com.momc.admin.application.controller.admin.form.AdminRegisterForm;
import com.momc.admin.domain.admin.entity.Admin;
import com.momc.admin.domain.admin.entity.AdminRole;
import com.momc.admin.domain.admin.entity.AdminStatus;
import com.momc.admin.domain.admin.repository.AdminRepository;
import com.momc.admin.domain.admin.service.objects.StubAdminRepository;
import com.momc.admin.utils.AdminFixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AdminRegisterServiceTest {

    AdminRegisterService adminRegisterService;
    AdminRepository adminRepository;

    @BeforeEach
    void setUp() {
        adminRepository = new StubAdminRepository();
        adminRegisterService = new AdminRegisterServiceImpl(adminRepository, oriPassword -> oriPassword.repeat(2));
    }

    @Test
    void testRegister() {
        AdminRegisterForm request = AdminFixtureFactory.createDefaultRegisterDto();
        adminRegisterService.register(request);

        Admin admin = adminRepository.findById(1).orElseThrow(() -> new RuntimeException());
        assertThat(admin.getLoginId()).isEqualTo(request.getLoginId());
        assertThat(admin.getAdminName()).isEqualTo(request.getAdminName());
        assertThat(admin.getPassword()).isEqualTo(request.getPassword());
        assertThat(admin.getPassword()).isNotEqualTo("1234");
        assertThat(admin.getRole()).isEqualTo(AdminRole.ADMIN);
        assertThat(admin.getStatus()).isEqualTo(AdminStatus.WAIT);
    }

    @Test
    void testRegisterWithDuplicateIds() {
        AdminRegisterForm request = AdminFixtureFactory.createDefaultRegisterDto();
        adminRegisterService.register(request);

        assertThatThrownBy(() -> adminRegisterService.register(request)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testApprove() {
        AdminRegisterForm request = AdminFixtureFactory.createDefaultRegisterDto();
        adminRegisterService.register(request);

        Admin admin = adminRepository.findById(1).orElseThrow(() -> new RuntimeException());
        assertThat(admin.getStatus()).isEqualTo(AdminStatus.WAIT);

        adminRegisterService.approveRegister(admin.getId());
        assertThat(admin.getStatus()).isEqualTo(AdminStatus.APPROVAL);
    }

    @Test
    void testReject() {
        AdminRegisterForm request = AdminFixtureFactory.createDefaultRegisterDto();
        adminRegisterService.register(request);

        Admin admin = adminRepository.findById(1).orElseThrow(() -> new RuntimeException());
        adminRegisterService.rejectRegister(admin.getId());

        assertThat(adminRepository.findById(admin.getId())).isEmpty();
    }
}