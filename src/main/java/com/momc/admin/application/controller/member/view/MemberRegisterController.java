package com.momc.admin.application.controller.member.view;

import com.momc.admin.application.controller.member.form.MemberRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberRegisterController {

    @GetMapping("/join")
    public String sendMemberJoinView(@ModelAttribute("memberRegisterForm") MemberRegisterForm form) {
        return "member/member-join";
    }
}
