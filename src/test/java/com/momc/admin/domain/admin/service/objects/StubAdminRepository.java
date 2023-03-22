package com.momc.admin.domain.admin.service.objects;

import com.momc.admin.domain.admin.dto.AdminDto;
import com.momc.admin.domain.admin.dto.WaitingAdminDto;
import com.momc.admin.domain.admin.entity.Admin;
import com.momc.admin.domain.admin.entity.AdminStatus;
import com.momc.admin.domain.admin.repository.AdminRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StubAdminRepository implements AdminRepository {

    private final Map<Integer, Admin> map = new HashMap<>();
    private Integer id = 1;

    @Override
    public boolean existsByLoginIdOrAdminName(String loginId, String adminName) {
        return map.values()
                .stream()
                .anyMatch(admin -> admin.getLoginId().equals(loginId) || admin.getAdminName().equals(adminName));
    }

    @Override
    public List<AdminDto> findAllApprovedAdmin() {
        return map.values()
                .stream()
                .filter(admin -> admin.getStatus() == AdminStatus.APPROVAL)
                .map(admin -> AdminDto.builder()
                        .id(admin.getId())
                        .loginId(admin.getLoginId())
                        .adminName(admin.getAdminName())
                        .role(admin.getRole())
                        .createdDate(admin.getCreatedDate())
                        .lastModifiedDate(admin.getLastModifiedDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<WaitingAdminDto> findAllWaitingAdmin() {
        return map.values()
                .stream()
                .filter(admin -> admin.getStatus() == AdminStatus.WAIT)
                .map(admin -> WaitingAdminDto.builder()
                        .id(admin.getId())
                        .loginId(admin.getLoginId())
                        .adminName(admin.getAdminName())
                        .createdDate(admin.getCreatedDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Admin> findAll() {
        return null;
    }

    @Override
    public List<Admin> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<Admin> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public <S extends Admin> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Admin> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Admin> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Admin> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Admin getOne(Integer integer) {
        return null;
    }

    @Override
    public Admin getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Admin> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Admin> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Admin> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Admin> S save(S entity) {
        Admin adminProxy = new AdminProxy(id++, entity);
        map.put(adminProxy.getId(), adminProxy);

        return (S) adminProxy;
    }

    @Override
    public Optional<Admin> findById(Integer integer) {
        return Optional.ofNullable(map.get(integer));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Admin entity) {
        map.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Admin> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Admin> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Admin> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Admin> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Admin> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Admin, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Optional<Admin> findByLoginId(String loginId) {
        return null;
    }
}