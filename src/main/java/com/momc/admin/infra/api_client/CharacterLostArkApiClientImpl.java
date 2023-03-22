package com.momc.admin.infra.api_client;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.characters.dto.LostArkCharacterApiResult;
import com.momc.admin.domain.characters.dto.LostArkCharacterDetailApiResult;
import com.momc.admin.domain.characters.infra.CharacterLostArkApiClient;
import com.momc.admin.domain.characters.infra.exception.CannotFindCharacterException;
import com.momc.admin.utils.exchanger.RestTemplateExchanger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ConstructorBinding
@ConfigurationProperties("momc.api")
public class CharacterLostArkApiClientImpl implements CharacterLostArkApiClient {

    private final String baseUrl;
    private final List<String> apiKeys;
    private final String characterPath;
    private final String characterDetailPath;
    private static final String GUILD_SERVER_NAME = "아만";

    public CharacterLostArkApiClientImpl(String baseUrl, List<String> apiKeys, String characterPath, String characterDetailPath) {
        this.baseUrl = baseUrl;
        this.apiKeys = apiKeys;
        this.characterPath = characterPath;
        this.characterDetailPath = characterDetailPath;
    }

    @Override
    public List<String> getAllCharacterNamesByMainCharacter(String characterName, boolean await) {
        LostArkCharacterApiResult[] responseBody = exchange(LostArkCharacterApiResult[].class, characterPath, characterName, await);

        return Arrays.stream(responseBody)
                .filter(result -> result.getServerName().equals(GUILD_SERVER_NAME))
                .map(LostArkCharacterApiResult::getCharacterName)
                .collect(Collectors.toList());
    }

    @Override
    public List<CharacterDetailData> getAllCharacterDetailDataByMainCharacter(String characterName, boolean await) {
        return getAllCharacterDetailData(getAllCharacterNamesByMainCharacter(characterName, await), await);
    }

    @Override
    public List<CharacterDetailData> getAllCharacterDetailData(List<String> characterNames, boolean await) {
        List<LostArkCharacterDetailApiResult> detailApiResults = exchangeAll(
                LostArkCharacterDetailApiResult.class,
                characterDetailPath,
                characterNames,
                await
        );
        return detailApiResults.stream()
                .map(this::convertToDetailData)
                .collect(Collectors.toList());
    }

    @Override
    public CharacterDetailData getCharacterDetailData(String characterName, boolean await) {
        LostArkCharacterDetailApiResult responseBody = exchange(
                LostArkCharacterDetailApiResult.class,
                characterDetailPath,
                characterName,
                await
        );

        return convertToDetailData(responseBody);
    }

    private CharacterDetailData convertToDetailData(LostArkCharacterDetailApiResult responseBody) {
        return CharacterDetailData.builder()
                .expeditionLevel(responseBody.getExpeditionLevel())
                .characterName(responseBody.getCharacterName())
                .className(responseBody.getCharacterClassName())
                .combatLevel(responseBody.getCharacterLevel())
                .itemLevel(responseBody.getItemAvgLevelAsDouble())
                .guildName(responseBody.getGuildName())
                .guildMemberGrade(responseBody.getGuildMemberGrade())
                .build();
    }

    private <T> T exchange(Class<T> responseType, String path, String characterName, boolean await) {
        RestTemplateExchanger exchanger = RestTemplateExchanger.createDefault(apiKeys, baseUrl, path, await);
        ResponseEntity<T> responseEntity = exchanger.exchange(responseType, characterName);
        validResponse(responseEntity.getBody(), characterName);

        return responseEntity.getBody();
    }

    private void validResponse(Object body, String characterName) {
        if (body == null){
            throw new CannotFindCharacterException(String.format(
                    "캐릭터를 찾을수 없습니다. 정확한 캐릭터 이름을 사용하세요 = %s",
                    characterName
            ));
        }
    }

    private <T> List<T> exchangeAll(Class<T> responseType, String path, List<String> characterNames, boolean await) {
        RestTemplateExchanger exchanger = RestTemplateExchanger.createDefault(apiKeys, baseUrl, path, await);
        List<ResponseEntity<T>> responseEntities = exchanger.exchangeAll(responseType, characterNames);
        return responseEntities.stream()
                .map(ResponseEntity::getBody)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
