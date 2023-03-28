package com.momc.admin.application.controller.admin.api;

import com.momc.admin.application.controller.common.ApiResponse;
import com.momc.admin.application.controller.common.ApiResponseFactory;
import com.momc.admin.domain.admin.service.AdminRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins/waits")
public class AdminApproveApiController {

    private final AdminRegisterService adminRegisterService;

    @PostMapping("/{adminId}")
    public ApiResponse approveRegister(@PathVariable("adminId") Integer adminId) {
        adminRegisterService.approveRegister(adminId);
        return ApiResponseFactory.ok(adminId);
    }

    @DeleteMapping("/{adminId}")
    public ApiResponse rejectRegister(@PathVariable("adminId") Integer adminId) {
        adminRegisterService.rejectRegister(adminId);
        return ApiResponseFactory.ok(adminId);
    }
}
