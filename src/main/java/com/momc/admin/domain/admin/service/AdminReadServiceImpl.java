package com.momc.admin.domain.admin.service;

import com.momc.admin.domain.admin.dto.AdminDto;
import com.momc.admin.domain.admin.dto.WaitingAdminDto;
import com.momc.admin.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class AdminReadServiceImpl implements AdminReadService {

    private final AdminRepository adminRepository;

    @Override
    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAllApprovedAdmin();
    }

    @Override
    public List<WaitingAdminDto> getAllWaitingAdmins() {
        return adminRepository.findAllWaitingAdmin();
    }
}
