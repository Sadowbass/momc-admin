package com.momc.admin.domain.characters.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DuplicateCharacterException extends RuntimeException {

    private final List<String> duplicateCharacterNames;

    public DuplicateCharacterException(List<String> duplicateCharacterNames) {
        super(String.format("이미 등록된 캐릭터입니다. 회원목록을 확인하세요. %s", duplicateCharacterNames));
        this.duplicateCharacterNames = duplicateCharacterNames;
    }
}
