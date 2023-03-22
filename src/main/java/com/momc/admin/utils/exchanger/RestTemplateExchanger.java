package com.momc.admin.utils.exchanger;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestTemplateExchanger {

    static RestTemplateExchanger createDefault(List<String> apiKeys, String httpUrl, String path, boolean await) {
        RestTemplateExchangerBuilder builder = new RestTemplateExchangerBuilder(apiKeys, httpUrl, path);

        if (await) {
            builder.await();
        }

        return builder.build();
    }

    <T> ResponseEntity<T> exchange(Class<T> responseType, Object uriVariables);

    <T> List<ResponseEntity<T>> exchangeAll(Class<T> responseType, List<?> uriVariables);
}
