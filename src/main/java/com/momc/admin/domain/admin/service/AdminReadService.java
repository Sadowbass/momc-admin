package com.momc.admin.domain.admin.service;

import com.momc.admin.domain.admin.dto.AdminDto;
import com.momc.admin.domain.admin.dto.WaitingAdminDto;

import java.util.List;

public interface AdminReadService {

    List<AdminDto> getAllAdmins();

    List<WaitingAdminDto> getAllWaitingAdmins();
}
