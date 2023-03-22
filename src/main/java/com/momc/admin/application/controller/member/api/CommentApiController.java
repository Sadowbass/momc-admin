package com.momc.admin.application.controller.member.api;

import com.momc.admin.application.controller.common.ApiResponse;
import com.momc.admin.application.controller.common.ApiResponseFactory;
import com.momc.admin.application.controller.member.form.CommentWriteForm;
import com.momc.admin.application.service.CommentAppService;
import com.momc.admin.domain.member.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class CommentApiController {

    private final CommentAppService commentAppService;

    @PostMapping("/{memberId}/comments")
    public ApiResponse writeNewComment(@PathVariable Integer memberId, @RequestBody @Valid CommentWriteForm form) {
        List<CommentDto> allComments = commentAppService.writeNewComment(memberId, form);
        return ApiResponseFactory.ok(allComments);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse handleBindException(BindException ex) {
        return ApiResponseFactory.custom()
                .rspCode(HttpStatus.BAD_REQUEST)
                .rspMessage(ex.getMessage())
                .build();
    }
}
