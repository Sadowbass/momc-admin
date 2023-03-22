package com.momc.admin.domain.characters.infra;

import com.momc.admin.domain.characters.dto.CharacterDetailData;

import java.util.List;

public interface CharacterLostArkApiClient {

    List<String> getAllCharacterNamesByMainCharacter(String characterName, boolean await);

    CharacterDetailData getCharacterDetailData(String characterName, boolean await);

    List<CharacterDetailData> getAllCharacterDetailData(List<String> characterNames, boolean await);

    List<CharacterDetailData> getAllCharacterDetailDataByMainCharacter(String characterName, boolean await);
}
