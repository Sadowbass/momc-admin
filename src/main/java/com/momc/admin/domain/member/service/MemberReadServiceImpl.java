package com.momc.admin.domain.member.service;

import com.momc.admin.domain.member.dto.CommentDto;
import com.momc.admin.domain.member.dto.MemberInfo;
import com.momc.admin.domain.member.dto.MemberInfoDetail;
import com.momc.admin.domain.member.repository.MemberRepository;
import com.momc.admin.utils.PageObject;
import com.momc.admin.utils.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberReadServiceImpl implements MemberReadService {

    private final MemberRepository memberRepository;

    @Override
    public PageObject<MemberInfo> getAllJoinedMembers(PageRequest pageRequest) {
        return memberRepository.findAllJoinedMembers(pageRequest);
    }

    @Override
    public PageObject<MemberInfo> getAllLeftMembers(PageRequest pageRequest) {
        return memberRepository.findAllLeftMembers(pageRequest);
    }

    @Override
    public PageObject<MemberInfo> findMembersByIds(PageRequest pageRequest, List<Integer> memberIds) {
        return memberRepository.findMembersByIdIn(pageRequest, memberIds);
    }

    @Override
    public MemberInfoDetail getMemberInfoDetail(Integer memberId) {
        MemberInfoDetail detail = memberRepository.findMemberInfoDetailByMemberId(memberId);
        List<CommentDto> sortedComment = detail.getComments()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        detail.setComments(sortedComment);
        return detail;
    }
}
