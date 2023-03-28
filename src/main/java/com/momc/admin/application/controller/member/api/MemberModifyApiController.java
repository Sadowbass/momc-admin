package com.momc.admin.application.controller.member.api;

import com.momc.admin.application.controller.common.ApiResponse;
import com.momc.admin.application.controller.common.ApiResponseFactory;
import com.momc.admin.application.controller.member.form.MemberModifyForm;
import com.momc.admin.application.service.MemberAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberModifyApiController {

    private final MemberAppService memberAppService;

    @PatchMapping("/{memberId}")
    public ApiResponse modifyMember(@PathVariable Integer memberId, @RequestBody MemberModifyForm form) {
        memberAppService.modifyMember(memberId, form.getMainCharacterId(), form.getJoinDate());

        return ApiResponseFactory.ok();
    }
}
