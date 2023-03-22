package com.momc.admin.domain.admin.service;

import com.momc.admin.infra.config.security.auth.AdminCredential;
import com.momc.admin.domain.admin.entity.Admin;
import com.momc.admin.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminRepository adminRepository;

    @Override
    public AdminCredential getLoginInfoByLoginId(String loginId) {
        Admin admin = adminRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException("ID가 존재하지 않습니다."));
        return new AdminCredential(admin);
    }
}
