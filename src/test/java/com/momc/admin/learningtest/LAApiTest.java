package com.momc.admin.learningtest;

import com.momc.admin.domain.characters.dto.LostArkCharacterDetailApiResult;
import com.momc.admin.utils.ApiKeyHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class LAApiTest {

    String baseUrl = "https://developer-lostark.game.onstove.com";
    String path = "/armories/characters/{characterName}/profiles";

    String characterName = "개발하다샷건침";
    List<String> characterNames = new ArrayList<>();

    String apiKey1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDAwNTcwMTgifQ.PvhGFFjKexQFIynwTQIAys3zq6bFVzMwCbr4Vnd7HiwtAKcy2XEblgQ56NlFYWByFrN2FbMk5ISo0RyKggJaJ5SbC_OrFo-RLbA--Ks8iAL3tgyrzn8SLsCMJ8FC9zGFnyZ90xnbWoqeXf6sAPRg3hMlsoMm7WpYO2qwAAxlaPpIXHy_TP2X38IBKbuloc_sm6pfFjPB3NFkH-qgR994qjy0zjiPaVekjIYxtmABwXNAIn7U_LHu-AswsyybepYRmgX74N5nYkdAZQTZQTqzvmWnk89wuE8tEh9U5A_qXCUTD29kgPvUOsM3bR-PexUlgbCElWGcIVvdSQ3avFHrcw";
    String apiKey2 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDAwNTcwMTcifQ.hATGIhBhRGp6GiH9FzZoJqyVSq3zeuT4RCmf4BEutPs54XMM_TV9JJ9whck5qhCWNHhb3mBL0DInBoNL9fQPIWbpj3Inib3nnHFu8Wr3E8_OBFLoUhVLjqi5RGefrfDsjCCjtpShIB1sNWwvyCDU87-cxGcXcqGi5nHmb6uOckNea3vdfg3KJvpz_dMkkoGv0LCsVe_7-wMeqoTHdZo6sXHmCOTc4od3UYL5l9pwy_IeQS9FiCJ5KUwZmh2SH-wNijrZDZvOHJCWEOIUmTIo_yoKpQMk8CiIvqNBz2yFOesTuHF1uwVWaAViXyXQGBiX5_GttDgG_6hkbteTwPax3A";
    List<String> apiKeys = List.of(apiKey1, apiKey2);

    RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    public void init() {
        for (int i = 0; i < 220; i++) {
            characterNames.add(characterName);
        }
        restTemplate.setErrorHandler(new TestErrorHandler());
    }

    @Test
    void test() {
        int requestLimitPerMinute = 100;

        List<List<String>> requestList = new ArrayList<>();
        List<String> currentList = new ArrayList<>();

        for (int i = 0; i < characterNames.size(); i++) {
            if (i % requestLimitPerMinute == 0) {
                currentList = new ArrayList<>();
                requestList.add(currentList);
            }

            currentList.add(characterNames.get(i));
        }

        ApiKeyHolder keyController = new ApiKeyHolder(apiKeys);
        for (int i = 0; i <= requestList.size() / apiKeys.size(); i++) {
            int startIndex = i * apiKeys.size();
            int endIndex = Math.min(requestList.size(), i * apiKeys.size() + apiKeys.size());

            List<LostArkCharacterDetailApiResult> collect = IntStream.range(startIndex, endIndex)
                    .parallel()
                    .mapToObj(currentIndex -> executeEach(
                            requestList.get(currentIndex),
                            keyController.getNextKey(),
                            LostArkCharacterDetailApiResult.class)
                    )
                    .flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }

    private <T> List<T> executeEach(List<String> characterNames, String apiKey, Class<T> responseType) {
        Integer errorCount = 0;
        return characterNames.stream()
                .map(name -> loopExchange(name, errorCount, apiKey, responseType))
                .collect(Collectors.toList());
    }


    private <T> T loopExchange(String characterName, Integer errorCount, String apiKey, Class<T> responseType) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path(path).build(characterName);

        while (errorCount < 3) {
            ResponseEntity<T> result = exchange(createHttpEntity(apiKey), uri, responseType);
            if (result.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                errorCount++;
                sleep(result.getHeaders().get("Retry-After").get(0));
            } else {
                return result.getBody();
            }
        }

        throw new RuntimeException("지속적인 request 초과");
    }

    private <T> ResponseEntity<T> exchange(HttpEntity<Object> httpEntity, URI uri, Class<T> responseType) {
        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );
    }

    private static void sleep(String strRetrySecond) {
        try {
            TimeUnit.SECONDS.sleep(Long.parseLong(strRetrySecond) + 1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private HttpEntity<Object> createHttpEntity(String apiKey) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(apiKey);

        return new HttpEntity<>(httpHeaders);
    }

    static class TestErrorHandler extends DefaultResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                System.out.println(response.getHeaders());
                return;
            }
            super.handleError(response);
        }
    }
}
