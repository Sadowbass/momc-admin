package com.momc.admin.utils.exchanger;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Getter(AccessLevel.PROTECTED)
abstract class AbstractRestTemplateExchanger implements RestTemplateExchanger {

    private RestTemplate restTemplate;
    private List<String> apiKeys;
    private String httpUrl;
    private String path;

    protected AbstractRestTemplateExchanger(RestTemplate restTemplate, List<String> apiKeys, String httpUrl, String path) {
        this.restTemplate = restTemplate;
        restTemplate.setErrorHandler(new TooManyRequestErrorHandler());

        this.apiKeys = apiKeys;
        this.httpUrl = httpUrl;
        this.path = path;
    }

    protected HttpEntity<Object> createHttpEntity(String apiKey) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.setBearerAuth(apiKey);

        return new HttpEntity<>(httpHeaders);
    }

    protected <T> ResponseEntity<T> exchange(URI uri, HttpEntity<Object> httpEntity, Class<T> responseType) {
        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );
    }
}
