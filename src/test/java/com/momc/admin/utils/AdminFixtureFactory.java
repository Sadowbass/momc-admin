package com.momc.admin.utils;

import com.momc.admin.application.controller.admin.form.AdminRegisterForm;

public class AdminFixtureFactory {

    public static AdminRegisterForm createDefaultRegisterDto() {
        return createRegisterDto("daum123", "샷건", "1234");
    }

    public static AdminRegisterForm createRegisterDto(String loginId, String adminName, String password) {
        return new AdminRegisterForm(loginId, adminName, password);
    }
}
