package com.momc.admin.application.controller.admin.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterForm {

    @NotEmpty(message = "아이디는 필수입니다")
    private String loginId;

    @NotEmpty(message = "캐릭터명은 필수입니다")
    private String adminName;

    @NotEmpty(message = "비밀번호는 필수입니다")
    private String password;
}
