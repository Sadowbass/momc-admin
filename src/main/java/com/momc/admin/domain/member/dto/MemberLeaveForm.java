package com.momc.admin.domain.member.dto;

import com.momc.admin.domain.member.entity.CommentCategory;
import com.momc.admin.domain.member.entity.MemberStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MemberLeaveForm {

    private MemberStatus reason;
    private CommentCategory commentType;
    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate leaveDate;
}
