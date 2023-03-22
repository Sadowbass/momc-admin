package com.momc.admin.application.service;

import com.momc.admin.application.controller.member.form.CommentWriteForm;
import com.momc.admin.domain.member.dto.CommentDto;
import com.momc.admin.domain.member.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentAppService {

    private final CommentService commentService;

    @Transactional
    public List<CommentDto> writeNewComment(Integer memberId, CommentWriteForm form) {
        return commentService.writeNewComment(memberId, form.getCommentCategory(), form.getContent());
    }
}
