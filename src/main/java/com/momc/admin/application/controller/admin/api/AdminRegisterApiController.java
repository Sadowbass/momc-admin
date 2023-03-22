package com.momc.admin.application.controller.admin.api;

import com.momc.admin.domain.admin.service.AdminRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminRegisterApiController {

    private final AdminRegisterService adminRegisterService;

//    @PostMapping
//    public ApiResponse register(@RequestBody @Valid AdminRegisterForm form, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            List<String> errorMessages = bindingResult.getAllErrors()
//                    .stream()
//                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                    .collect(Collectors.toList());
//            return ApiResponseFactory.badRequest(errorMessages);
//        }
//
//        adminRegisterService.register(form);
//        return ApiResponseFactory.ok();
//    }
}
