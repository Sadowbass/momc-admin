package com.momc.admin.application.controller.member.form;

import com.momc.admin.domain.member.entity.CommentCategory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CommentWriteForm {

    private CommentCategory commentCategory;
    @NotEmpty
    private String content;
}
