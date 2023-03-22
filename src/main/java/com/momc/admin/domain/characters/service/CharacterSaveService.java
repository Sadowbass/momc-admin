package com.momc.admin.domain.characters.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;

import java.util.List;
import java.util.Map;

public interface CharacterSaveService {

    List<CharacterDetailData> saveAllCharacters(Integer memberId, List<CharacterDetailData> characterDetailDataList);

    Map<String, CharacterDetailData> updateAllCharacters(Map<String, CharacterDetailData> detailDataMap);
}
