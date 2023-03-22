package com.momc.admin.application.controller.member.view;

import com.momc.admin.application.controller.member.form.MemberSearchCondition;
import com.momc.admin.application.service.CharacterAppService;
import com.momc.admin.application.service.MemberAppService;
import com.momc.admin.domain.member.dto.MemberInfo;
import com.momc.admin.domain.member.dto.MemberInfoDetail;
import com.momc.admin.utils.PageObject;
import com.momc.admin.utils.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberReadController {

    private final MemberAppService memberAppService;
    private final CharacterAppService characterAppService;

    @GetMapping
    public String sendAllJoinedMembersView(PageRequest pageRequest, MemberSearchCondition condition, Model model) {
        PageObject<MemberInfo> members;

        if (ObjectUtils.isEmpty(condition.getCharacterName())) {
            members = memberAppService.getAllJoinedMember(pageRequest);
        } else {
            members = memberAppService.findMembersByCharacterName(pageRequest, condition.getCharacterName());
        }

        model.addAttribute("pages", members);

        List<String> characterNames = characterAppService.getAllCharacterNames();
        model.addAttribute("characterNames", characterNames);

        return "member/member-list";
    }

    @GetMapping("/{memberId}")
    public String sendMemberDetailView(@PathVariable Integer memberId, Model model) {
        MemberInfoDetail memberDetail = memberAppService.getMemberDetail(memberId);
        model.addAttribute("memberDetail", memberDetail);

        return "member/member-detail";
    }

    @GetMapping("/lefts")
    public String sendAllLeftMembersView(PageRequest pageRequest, Model model) {
        PageObject<MemberInfo> leftMembers = memberAppService.getAllLeftMembers(pageRequest);
        model.addAttribute("pages", leftMembers);

        return "member/member-list";
    }
}
