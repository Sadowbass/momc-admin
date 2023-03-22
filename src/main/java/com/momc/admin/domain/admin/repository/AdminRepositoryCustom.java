package com.momc.admin.domain.admin.repository;

import com.momc.admin.domain.admin.dto.AdminDto;
import com.momc.admin.domain.admin.dto.WaitingAdminDto;

import java.util.List;

public interface AdminRepositoryCustom {

    List<AdminDto> findAllApprovedAdmin();

    List<WaitingAdminDto> findAllWaitingAdmin();
}
