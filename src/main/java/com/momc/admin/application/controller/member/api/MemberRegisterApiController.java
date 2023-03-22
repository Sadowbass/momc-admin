package com.momc.admin.application.controller.member.api;

import com.momc.admin.application.controller.common.ApiResponse;
import com.momc.admin.application.controller.common.ApiResponseFactory;
import com.momc.admin.application.controller.member.form.MemberRegisterForm;
import com.momc.admin.application.service.MemberAppService;
import com.momc.admin.domain.characters.exception.DuplicateCharacterException;
import com.momc.admin.domain.member.dto.MemberLeaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberRegisterApiController {

    private final MemberAppService memberAppService;

    @PostMapping
    public void registerNewMember(@RequestBody @Valid MemberRegisterForm form) {
        memberAppService.registerNewMember(form.getCharacterName(), form.getJoinDate());
    }

    @DeleteMapping("/{memberId}")
    public void leaveMember(@PathVariable("memberId") Integer memberId, @RequestBody MemberLeaveForm form) {
        memberAppService.leaveMember(memberId, form);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse handleBindException(BindException e) {
        log.error(this.getClass().getSimpleName());
        log.error(e.getMessage());

        return ApiResponseFactory.badRequest();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateCharacterException.class)
    public ApiResponse handleDuplicateCharacterException(DuplicateCharacterException e) {
        return ApiResponseFactory.custom()
                .rspCode(HttpStatus.BAD_REQUEST)
                .rspMessage(e.getMessage())
                .result(e.getDuplicateCharacterNames()).build();
    }
}
