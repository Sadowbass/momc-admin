package com.momc.admin.utils.exchanger;

import com.momc.admin.domain.characters.infra.exception.TooMuchRequestException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class DefaultRestTemplateExchanger extends AbstractRestTemplateExchanger {

    public DefaultRestTemplateExchanger(RestTemplate restTemplate, List<String> apiKeys, String httpUrl, String path) {
        super(restTemplate, apiKeys, httpUrl, path);
    }

    @Override
    public <T> ResponseEntity<T> exchange(Class<T> responseType, Object uriVariables) {
        URI uri = UriComponentsBuilder.fromHttpUrl(getHttpUrl()).path(getPath()).build(uriVariables);
        return exchange(uri, responseType);
    }

    @Override
    public <T> List<ResponseEntity<T>> exchangeAll(Class<T> responseType, List<?> uriVariables) {
        return uriVariables.stream()
                .parallel()
                .map(variable -> exchange(responseType, variable))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private <T> ResponseEntity<T> exchange(URI uri, Class<T> responseType) {
        for (String apiKey : getApiKeys()) {
            HttpEntity<Object> httpEntity = createHttpEntity(apiKey);
            ResponseEntity<T> result = exchange(uri, httpEntity, responseType);

            if (result.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                continue;
            }

            return result;
        }

        throw new TooMuchRequestException("모든 ApiKey가 허용된 요청량을 초과 하였습니다. 1분뒤 다시 이용해주세요.");
    }
}
