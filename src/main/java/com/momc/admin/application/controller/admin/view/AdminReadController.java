package com.momc.admin.application.controller.admin.view;

import com.momc.admin.domain.admin.dto.AdminDto;
import com.momc.admin.domain.admin.dto.WaitingAdminDto;
import com.momc.admin.domain.admin.service.AdminReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminReadController {

    private final AdminReadService adminReadService;

    @GetMapping
    public String sendAdminsView(Model model) {
        List<AdminDto> allAdmins = adminReadService.getAllAdmins();
        model.addAttribute("admins", allAdmins);

        return "admin/admin-list";
    }

    @GetMapping("/waits")
    public String sendWaitingAdminsView(Model model) {
        List<WaitingAdminDto> waitingAdmins = adminReadService.getAllWaitingAdmins();
        model.addAttribute("waitingAdmins", waitingAdmins);

        return "admin/admin-wait";
    }
}