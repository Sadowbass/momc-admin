package com.momc.admin.domain.admin.service;

import com.momc.admin.application.controller.admin.form.AdminRegisterForm;
import com.momc.admin.domain.admin.entity.AdminRole;

public interface AdminRegisterService {

    void register(AdminRegisterForm dto);

    void approveRegister(Integer id);

    void rejectRegister(Integer id);

    void deleteAdmin(Integer id, AdminRole editorRole);
}
