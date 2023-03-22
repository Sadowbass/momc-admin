package com.momc.admin.application.controller.admin.api;

import com.momc.admin.domain.admin.service.AdminRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins/waits")
public class AdminApproveApiController {

    private final AdminRegisterService adminRegisterService;

    @PostMapping("/{adminId}")
    public Integer approveRegister(@PathVariable("adminId") Integer adminId) {
        adminRegisterService.approveRegister(adminId);
        return adminId;
    }

    @DeleteMapping("/{adminId}")
    public Integer rejectRegister(@PathVariable("adminId") Integer adminId) {
        adminRegisterService.rejectRegister(adminId);
        return adminId;
    }
}
