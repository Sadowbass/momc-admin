package com.momc.admin.infra.config.security.auth;

import com.momc.admin.domain.admin.service.AdminLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminLoginService adminLoginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminCredential loginInfo = adminLoginService.getLoginInfoByLoginId(username);
        return new AdminUserDetails(loginInfo);
    }
}
