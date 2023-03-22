package com.momc.admin.utils;

import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Random;

public class ApiKeyHolder {

    private final List<String> apiKeys;
    private final Random random;
    private int keyIndex;

    public ApiKeyHolder(List<String> apiKeys) {
        if (ObjectUtils.isEmpty(apiKeys)) {
            throw new RuntimeException("required non null or empty list");
        }
        this.apiKeys = apiKeys;
        this.random = new Random();
        this.keyIndex = 0;
    }

    public synchronized String getNextKey() {
        if (keyIndex == apiKeys.size()) {
            keyIndex = 0;
        }

        return apiKeys.get(keyIndex++);
    }

    public String getRandomKey() {
        return apiKeys.get(random.nextInt(apiKeys.size()));
    }

    public int size() {
        return apiKeys.size();
    }
}
