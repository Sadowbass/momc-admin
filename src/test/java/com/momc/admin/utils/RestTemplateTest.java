package com.momc.admin.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

class RestTemplateTest {

    RestTemplate restTemplate;
    String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDAwNTcwMTgifQ.PvhGFFjKexQFIynwTQIAys3zq6bFVzMwCbr4Vnd7HiwtAKcy2XEblgQ56NlFYWByFrN2FbMk5ISo0RyKggJaJ5SbC_OrFo-RLbA--Ks8iAL3tgyrzn8SLsCMJ8FC9zGFnyZ90xnbWoqeXf6sAPRg3hMlsoMm7WpYO2qwAAxlaPpIXHy_TP2X38IBKbuloc_sm6pfFjPB3NFkH-qgR994qjy0zjiPaVekjIYxtmABwXNAIn7U_LHu-AswsyybepYRmgX74N5nYkdAZQTZQTqzvmWnk89wuE8tEh9U5A_qXCUTD29kgPvUOsM3bR-PexUlgbCElWGcIVvdSQ3avFHrcw";
    String lostArkUrl = "https://developer-lostark.game.onstove.com/";


    @BeforeEach
    void setUp() {
        this.restTemplate = new RestTemplate();
    }

    @Test
    void test() {
        String filename = System.getProperty("java.home") + "/lib/security/cacerts".replace('/', File.separatorChar);
        System.out.println(filename);

        HttpHeaders httpHeaders = new HttpHeaders();
//        String path = "https://www.naver.com";
        String path = "https://developer-lostark.game.onstove.com";
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> exchange = template.exchange(path, HttpMethod.GET, null, String.class, new HashMap<>());
        System.out.println(exchange.getBody());
    }

    @Test
    void testRestTemplate() throws JsonProcessingException {
        String path = "/armories/characters/{characterName}/profiles";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(apiKey);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        List<String> characterNames = List.of("개발하다샷건침", "비와서개발안함", "뒤에서도개발안함", "스프링코틀린", "개발못함", "자바못함", "소주코더", "개발자아님");

        characterNames.stream()
                .parallel()
                .map(characterName -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(lostArkUrl).path(path).build(characterName);
                    ResponseEntity<TestResponseObject> exchange = restTemplate.exchange(
                            uri,
                            HttpMethod.GET,
                            httpEntity,
                            TestResponseObject.class
                    );

                    TestResponseObject body = exchange.getBody();
                    if (null == body) {
                        //TODO create new exception
                        throw new RuntimeException("캐릭터를 찾을수 없습니다. 정확한 캐릭터 이름을 사용하세요");
                    }

                    return body;
                })
                .forEach(System.out::println);

    }

    @JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
    private static class TestResponseObject {

        private int expeditionLevel;
        private String guildMemberGrade;
        private String guildName;
        private String characterName;
        private int characterLevel;
        private String characterClassName;
        private String itemAvgLevel;

        public TestResponseObject() {
        }

        public int getExpeditionLevel() {
            return expeditionLevel;
        }

        public void setExpeditionLevel(int expeditionLevel) {
            this.expeditionLevel = expeditionLevel;
        }

        public String getGuildMemberGrade() {
            return guildMemberGrade;
        }

        public void setGuildMemberGrade(String guildMemberGrade) {
            this.guildMemberGrade = guildMemberGrade;
        }

        public String getGuildName() {
            return guildName;
        }

        public void setGuildName(String guildName) {
            this.guildName = guildName;
        }

        public String getCharacterName() {
            return characterName;
        }

        public void setCharacterName(String characterName) {
            this.characterName = characterName;
        }

        public int getCharacterLevel() {
            return characterLevel;
        }

        public void setCharacterLevel(int characterLevel) {
            this.characterLevel = characterLevel;
        }

        public String getCharacterClassName() {
            return characterClassName;
        }

        public void setCharacterClassName(String characterClassName) {
            this.characterClassName = characterClassName;
        }

        public String getItemAvgLevel() {
            return itemAvgLevel;
        }

        public void setItemAvgLevel(String itemAvgLevel) {
            this.itemAvgLevel = itemAvgLevel;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("TestResponseObject{");
            sb.append("expeditionLevel=").append(expeditionLevel);
            sb.append(", guildMemberGrade='").append(guildMemberGrade).append('\'');
            sb.append(", guildName='").append(guildName).append('\'');
            sb.append(", characterName='").append(characterName).append('\'');
            sb.append(", characterLevel=").append(characterLevel);
            sb.append(", characterClassName='").append(characterClassName).append('\'');
            sb.append(", itemAvgLevel='").append(itemAvgLevel).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
