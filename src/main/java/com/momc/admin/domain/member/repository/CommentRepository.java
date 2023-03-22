package com.momc.admin.domain.member.repository;

import com.momc.admin.domain.common.repository.BaseRepository;
import com.momc.admin.domain.member.entity.Comment;
import com.momc.admin.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends BaseRepository<Comment, Integer>, JpaRepository<Comment, Integer> {

    @Override
    default Comment getEntityByPkElseThrow(Integer commentId) {
        return findById(commentId).orElseThrow(() -> new RuntimeException("no comment find"));
    }

    List<Comment> findByMemberOrderByCreatedDateDesc(Member member);
}
