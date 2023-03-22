package com.momc.admin.domain.admin.repository;

import com.momc.admin.domain.admin.dto.AdminDto;
import com.momc.admin.domain.admin.dto.QAdminDto;
import com.momc.admin.domain.admin.dto.QWaitingAdminDto;
import com.momc.admin.domain.admin.dto.WaitingAdminDto;
import com.momc.admin.domain.admin.entity.AdminStatus;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.momc.admin.domain.admin.entity.QAdmin.admin;

public class AdminRepositoryCustomImpl implements AdminRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AdminRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<AdminDto> findAllApprovedAdmin() {
        return createQueryBySelectAndStatus(
                new QAdminDto(
                        admin.id, admin.loginId, admin.adminName, admin.role, admin.createdDate, admin.lastModifiedDate
                ),
                AdminStatus.APPROVAL
        ).fetch();
    }

    @Override
    public List<WaitingAdminDto> findAllWaitingAdmin() {
        return createQueryBySelectAndStatus(
                new QWaitingAdminDto(
                        admin.id, admin.loginId, admin.adminName, admin.createdDate
                ),
                AdminStatus.WAIT
        ).fetch();
    }

    private <T> JPAQuery<T> createQueryBySelectAndStatus(Expression<T> expression, AdminStatus status) {
        return queryFactory
                .select(expression)
                .from(admin)
                .where(admin.status.eq(status));
    }
}
