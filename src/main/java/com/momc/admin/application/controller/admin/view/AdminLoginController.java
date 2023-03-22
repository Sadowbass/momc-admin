package com.momc.admin.application.controller.admin.view;

import com.momc.admin.application.controller.admin.form.AdminLoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AdminLoginController {

    @GetMapping("/login")
    public String sendLoginView(@ModelAttribute("adminLoginForm") AdminLoginForm form) {
        return "login-form";
    }

    @PostMapping("/login-failed")
    public String loginFailed(
            @ModelAttribute("adminLoginForm")
            @Valid AdminLoginForm form,
            BindingResult bindingResult,
            HttpServletRequest request) {
        Object error = request.getAttribute("error");

        if (!ObjectUtils.isEmpty(error)) {
            bindingResult.addError(new ObjectError("form", error.toString()));
        }

        return "login-form";
    }

    @GetMapping("/expire")
    public String sessionExpired(@ModelAttribute("adminLoginForm") AdminLoginForm form) {
        return "redirect:/login?expired=true";
    }
}
