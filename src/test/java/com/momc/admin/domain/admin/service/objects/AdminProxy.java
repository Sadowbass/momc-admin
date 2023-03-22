package com.momc.admin.domain.admin.service.objects;

import com.momc.admin.domain.admin.entity.Admin;
import com.momc.admin.domain.admin.entity.AdminRole;
import com.momc.admin.domain.admin.entity.AdminStatus;

public class AdminProxy extends Admin {

    private Integer id;
    private Admin admin;

    public AdminProxy(Integer id, Admin admin) {
        super(admin.getLoginId(), admin.getAdminName(), admin.getPassword());
        this.id = id;
        this.admin = admin;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public AdminStatus getStatus() {
        return admin.getStatus();
    }

    @Override
    public String getLoginId() {
        return admin.getLoginId();
    }

    @Override
    public String getAdminName() {
        return admin.getAdminName();
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public AdminRole getRole() {
        return admin.getRole();
    }

    @Override
    public void approve() {
        admin.approve();
    }
}