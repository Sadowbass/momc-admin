package com.momc.admin.application.controller.admin.view;

import com.momc.admin.application.controller.admin.form.AdminRegisterForm;
import com.momc.admin.application.controller.common.ApiResponse;
import com.momc.admin.application.controller.common.ApiResponseFactory;
import com.momc.admin.application.service.AdminAppService;
import com.momc.admin.domain.admin.exception.DuplicateAdminIdException;
import com.momc.admin.domain.admin.service.AdminRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminRegisterController {

    private final AdminRegisterService adminRegisterService;
    private final AdminAppService adminAppService;

    private static final String HTML_JOIN_PAGE = "/join-form";
    private static final String FORM_NAME = "adminRegisterForm";

    @GetMapping("/register")
    public String sendRegisterView(@ModelAttribute(FORM_NAME) AdminRegisterForm form) {
        return HTML_JOIN_PAGE;
    }

    @PostMapping
    public String registerAdmin(
            @ModelAttribute(FORM_NAME)
            @Valid AdminRegisterForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return HTML_JOIN_PAGE;
        }

        adminRegisterService.register(form);
        return "redirect:/login";
    }

    @ResponseBody
    @DeleteMapping
    public ApiResponse deleteAdmin(@RequestBody Map<String, Integer> adminIdMap) {
        Integer id = adminIdMap.get("adminId");
        adminAppService.deleteAdmin(id);

        return ApiResponseFactory.ok(id);
    }

    @ExceptionHandler(DuplicateAdminIdException.class)
    public String duplicateIdExceptionHandler(DuplicateAdminIdException e, Model model) {
        AdminRegisterForm form = new AdminRegisterForm();
        model.addAttribute(FORM_NAME, form);

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(form, FORM_NAME);
        bindingResult.addError(new ObjectError(FORM_NAME, e.getMessage()));
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_NAME, bindingResult);

        return HTML_JOIN_PAGE;
    }
}
