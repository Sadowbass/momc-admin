package com.momc.admin.domain.admin.service;

import com.momc.admin.domain.admin.dto.AdminDto;
import com.momc.admin.application.controller.admin.form.AdminRegisterForm;
import com.momc.admin.domain.admin.dto.WaitingAdminDto;
import com.momc.admin.domain.admin.entity.Admin;
import com.momc.admin.domain.admin.repository.AdminRepository;
import com.momc.admin.domain.admin.service.objects.StubAdminRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class AdminReadServiceTest {

    AdminRepository adminRepository;
    AdminReadService adminReadService;
    List<Admin> savedAdmins;

    int saveNumber = 1000;
    int approveNumber = 100;
    int rejectNumber = 100;

    @BeforeEach
    void setUp() {
        adminRepository = new StubAdminRepository();
        adminReadService = new AdminReadServiceImpl(adminRepository);

        EasyRandom easyRandom = new EasyRandom();
        savedAdmins = IntStream.range(0, saveNumber)
                .mapToObj(i -> {
                    AdminRegisterForm dto = easyRandom.nextObject(AdminRegisterForm.class);
                    Admin admin = Admin.builder()
                            .loginId(dto.getLoginId())
                            .adminName(dto.getAdminName())
                            .password(dto.getPassword())
                            .build();
                    return adminRepository.save(admin);
                }).collect(Collectors.toList());

        for (int i = 0; i < rejectNumber; i++) {
            Admin admin = savedAdmins.get(0);
            adminRepository.delete(admin);
            savedAdmins.remove(admin);
        }

        for (int i = 0; i < approveNumber; i++) {
            Admin admin = savedAdmins.get(i);
            admin.approve();
        }
    }

    @Test
    void testFindAllApprovedAdmin() {
        List<AdminDto> allAdmins = adminReadService.getAllAdmins();
        assertThat(allAdmins).hasSize(approveNumber);

        IntStream.range(0, 5)
                .mapToObj(allAdmins::get)
                .forEach(System.out::println);
    }

    @Test
    void testFindAllWaitingAdmin() {
        List<WaitingAdminDto> waitingAdmins = adminReadService.getAllWaitingAdmins();
        assertThat(waitingAdmins).hasSize(saveNumber - approveNumber - rejectNumber);

        IntStream.range(0, 5)
                .mapToObj(waitingAdmins::get)
                .forEach(System.out::println);
    }
}