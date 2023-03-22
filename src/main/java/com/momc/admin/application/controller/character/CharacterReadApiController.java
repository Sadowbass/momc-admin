package com.momc.admin.application.controller.character;

import com.momc.admin.application.controller.character.dto.CharacterAudit;
import com.momc.admin.application.controller.common.ApiResponse;
import com.momc.admin.application.controller.common.ApiResponseFactory;
import com.momc.admin.application.service.CharacterAppService;
import com.momc.admin.domain.characters.infra.exception.CannotFindCharacterException;
import com.momc.admin.domain.characters.infra.exception.TooMuchRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharacterReadApiController {

    private final CharacterAppService characterAppService;

    @GetMapping("/{characterName}")
    public ApiResponse getCharacterInfo(@PathVariable String characterName) {
        CharacterAudit audit = characterAppService.getCharacterDataFromApiServer(characterName);
        return ApiResponseFactory.ok(audit);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CannotFindCharacterException.class)
    public ApiResponse handleCannotFindCharacterException(CannotFindCharacterException e) {
        return ApiResponseFactory.custom()
                .rspCode(HttpStatus.BAD_REQUEST)
                .rspMessage(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(TooMuchRequestException.class)
    public ApiResponse handleTooMuchRequestException(TooMuchRequestException e) {
        return ApiResponseFactory.custom()
                .rspCode(HttpStatus.TOO_MANY_REQUESTS)
                .rspMessage(e.getMessage())
                .build();
    }
}