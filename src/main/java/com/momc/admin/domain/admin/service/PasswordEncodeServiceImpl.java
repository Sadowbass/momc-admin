package com.momc.admin.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncodeServiceImpl implements PasswordEncodeService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String oriPassword) {
        return passwordEncoder.encode(oriPassword);
    }
}
