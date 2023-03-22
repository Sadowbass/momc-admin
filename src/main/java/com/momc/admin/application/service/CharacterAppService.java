package com.momc.admin.application.service;

import com.momc.admin.application.controller.character.dto.CharacterAudit;
import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.characters.infra.AccidentReport;
import com.momc.admin.domain.characters.infra.CharacterInvenAccidentApiClient;
import com.momc.admin.domain.characters.infra.CharacterLostArkApiClient;
import com.momc.admin.domain.characters.service.CharacterReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterAppService {

    private final CharacterLostArkApiClient characterLostArkApiClient;
    private final CharacterInvenAccidentApiClient characterInvenAccidentApiClient;
    private final CharacterReadService characterReadService;

    public CharacterAudit getCharacterDataFromApiServer(String characterName) {
        List<String> characterNames = characterLostArkApiClient.getAllCharacterNamesByMainCharacter(characterName, false);
        List<AccidentReport> accidentReports = characterInvenAccidentApiClient.searchAllAccidentPosts(characterNames);

        CharacterDetailData characterDetailDataDto = characterLostArkApiClient.getCharacterDetailData(characterName, false);
        String className = characterDetailDataDto.getClassName();
        int combatLevel = characterDetailDataDto.getCombatLevel();
        double itemLevel = characterDetailDataDto.getItemLevel();

        return new CharacterAudit(className, combatLevel, itemLevel, accidentReports);
    }

    public List<String> getAllCharacterNames() {
        return characterReadService.getAllCharacterNames();
    }
}
