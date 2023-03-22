package com.momc.admin.domain.admin.service;

import com.momc.admin.infra.config.security.auth.AdminCredential;

public interface AdminLoginService {

    AdminCredential getLoginInfoByLoginId(String loginId);

}
