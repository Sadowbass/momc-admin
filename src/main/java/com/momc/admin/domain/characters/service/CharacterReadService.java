package com.momc.admin.domain.characters.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;

import java.util.List;

public interface CharacterReadService {

    List<CharacterDetailData> getMembersAllCharacters(Integer memberId);

    List<Integer> getMemberIdsByCharacterNameLike(String characterName);

    List<String> getAllCharacterNames();
}
