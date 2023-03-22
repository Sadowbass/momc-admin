package com.momc.admin.application.controller.admin.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AdminLoginForm {

    @NotEmpty(message = "아이디는 필수입니다")
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수입니다")
    private String password;
}
