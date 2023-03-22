package com.momc.admin.infra.config.security.auth;

import com.momc.admin.domain.admin.entity.AdminRole;
import com.momc.admin.domain.admin.entity.AdminStatus;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class AdminUserDetails implements UserDetails {

    private final AdminCredential adminInfo;

    public AdminUserDetails(AdminCredential adminInfo) {
        this.adminInfo = adminInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(() -> adminInfo.getRole().name());

        return authorityList;
    }

    @Override
    public String getPassword() {
        return adminInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return adminInfo.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return adminInfo.getRole() == AdminRole.DEVELOPER || adminInfo.getStatus() == AdminStatus.APPROVAL;
    }
}
