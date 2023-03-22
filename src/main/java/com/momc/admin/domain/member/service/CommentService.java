package com.momc.admin.domain.member.service;

import com.momc.admin.domain.member.dto.CommentDto;
import com.momc.admin.domain.member.entity.CommentCategory;

import java.util.List;

public interface CommentService {

    List<CommentDto> writeNewComment(Integer memberId, CommentCategory commentCategory, String content);
}
