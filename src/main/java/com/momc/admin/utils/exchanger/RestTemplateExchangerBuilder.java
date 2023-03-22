package com.momc.admin.utils.exchanger;

import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class RestTemplateExchangerBuilder {

    private static final int DEFAULT_MAXIMUM_ERROR_COUNT = 3;

    private RestTemplate restTemplate;
    private final List<String> apiKeys;
    private final String httpUrl;
    private final String path;
    private boolean isAwait;
    private int maximumErrorCount = DEFAULT_MAXIMUM_ERROR_COUNT;

    public RestTemplateExchangerBuilder(List<String> apiKeys, String httpUrl, String path) {
        this.apiKeys = apiKeys;
        this.httpUrl = httpUrl;
        this.path = path;
    }

    public RestTemplateExchangerBuilder restTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        return this;
    }

    public RestTemplateExchangerBuilder await() {
        this.isAwait = true;
        return this;
    }

    public RestTemplateExchangerBuilder maximumErrorCount(int maximumErrorCount) {
        this.maximumErrorCount = maximumErrorCount;
        return this;
    }

    public AbstractRestTemplateExchanger build() {
        if (Objects.isNull(restTemplate)) {
            this.restTemplate = new RestTemplate();
        }

        AbstractRestTemplateExchanger exchanger;
        if (isAwait) {
            exchanger = new AwaitRestTemplateExchanger(restTemplate, apiKeys, maximumErrorCount, httpUrl, path);
        } else {
            exchanger = new DefaultRestTemplateExchanger(restTemplate, apiKeys, httpUrl, path);
        }

        return exchanger;
    }
}
