package com.momc.admin.domain.member.service;

import com.momc.admin.domain.member.dto.CommentDto;
import com.momc.admin.domain.member.entity.Comment;
import com.momc.admin.domain.member.entity.CommentCategory;
import com.momc.admin.domain.member.entity.Member;
import com.momc.admin.domain.member.repository.CommentRepository;
import com.momc.admin.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> writeNewComment(Integer memberId, CommentCategory commentCategory, String content) {
        Member member = memberRepository.getEntityByPkElseThrow(memberId);

        Comment comment = new Comment(member, commentCategory, content);
        commentRepository.save(comment);

        return mapToCommentDto(commentRepository.findByMemberOrderByCreatedDateDesc(member));
    }

    private List<CommentDto> mapToCommentDto(List<Comment> comments) {
        return comments.stream()
                .map(comment -> CommentDto.builder()
                        .id(comment.getId())
                        .memberId(comment.getMember().getId())
                        .commentCategory(comment.getCommentCategory())
                        .content(comment.getContent())
                        .author(comment.getCreatedBy())
                        .createdDate(comment.getCreatedDate())
                        .build())
                .sorted()
                .collect(Collectors.toList());
    }
}
