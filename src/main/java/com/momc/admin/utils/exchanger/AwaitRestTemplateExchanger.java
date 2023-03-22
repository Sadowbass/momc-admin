package com.momc.admin.utils.exchanger;

import com.momc.admin.utils.ApiKeyHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class AwaitRestTemplateExchanger extends AbstractRestTemplateExchanger {

    private final Integer maximumErrorCount;
    private final ApiKeyHolder apiKeyHolder;

    public AwaitRestTemplateExchanger(RestTemplate restTemplate, List<String> apiKeys, int maximumErrorCount, String httpUrl, String path) {
        super(restTemplate, apiKeys, httpUrl, path);
        this.maximumErrorCount = maximumErrorCount;
        this.apiKeyHolder = new ApiKeyHolder(apiKeys);
    }

    @Override
    public <T> ResponseEntity<T> exchange(Class<T> responseType, Object uriVariables) {
        Integer errorCount = 0;
        return loopExchange(errorCount, apiKeyHolder.getRandomKey(), responseType, uriVariables);
    }

    @Override
    public <T> List<ResponseEntity<T>> exchangeAll(Class<T> responseType, List<?> allRequest) {
        List<List<Object>> requestBlock = divideIntoBlocks(allRequest);

        List<ResponseEntity<T>> response = new ArrayList<>();
        for (int i = 0; i <= allRequest.size() / apiKeyHolder.size(); i++) {
            int startIndex = i * apiKeyHolder.size();
            int endIndex = Math.min(allRequest.size(), i * apiKeyHolder.size() + apiKeyHolder.size());

            List<ResponseEntity<T>> result = IntStream.range(startIndex, endIndex)
                    .parallel()
                    .mapToObj(currentIndex -> exchangeBlock(
                            apiKeyHolder.getNextKey(),
                            responseType,
                            requestBlock.get(currentIndex))
                    )
                    .flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            response.addAll(result);
        }

        return response;
    }

    private List<List<Object>> divideIntoBlocks(Object... requestList) {
        int requestLimitPerMinute = 100;

        List<List<Object>> requestBlock = new ArrayList<>();
        List<Object> currentList = new ArrayList<>();

        for (Object o : requestList) {
            currentList.add(o);

            if (currentList.size() == requestLimitPerMinute) {
                requestBlock.add(currentList);
                currentList = new ArrayList<>();
            }
        }

        return requestBlock;
    }

    private <T> List<ResponseEntity<T>> exchangeBlock(String apiKey, Class<T> responseType, List<Object> uriVariables) {
        Integer errorCount = 0;
        return uriVariables.stream()
                .map(variable -> loopExchange(errorCount, apiKey, responseType, variable))
                .collect(Collectors.toList());
    }

    private <T> ResponseEntity<T> loopExchange(Integer errorCount, String apiKey, Class<T> responseType, Object... uriVariables) {
        URI uri = UriComponentsBuilder.fromHttpUrl(getHttpUrl()).path(getPath()).build(uriVariables);

        while (errorCount < maximumErrorCount) {
            ResponseEntity<T> result = exchange(uri, createHttpEntity(apiKey), responseType);
            if (result.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                errorCount++;
                sleep(result.getHeaders().get("Retry-After").get(0));
            } else {
                return result;
            }
        }

        throw new RuntimeException("지속적으로 request 횟수를 초과했습니다.");
    }

    private static void sleep(String strRetrySecond) {
        try {
            TimeUnit.SECONDS.sleep(Long.parseLong(strRetrySecond) + 1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e.getMessage());
        }
    }
}
