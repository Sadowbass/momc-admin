package com.momc.admin.infra.config;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class PasswordEncoderHolder {

    private static final Map<String, PasswordEncoder> map = new HashMap<>();
    public static final String DEFAULT_ENCODER_NAME = "default";

    private PasswordEncoderHolder() {
        throw new UnsupportedOperationException();
    }

    public static void setDefaultPasswordEncoder(PasswordEncoder passwordEncoder) {
        setPasswordEncoder(DEFAULT_ENCODER_NAME, passwordEncoder);
    }

    public static void setPasswordEncoder(String encoderName, PasswordEncoder passwordEncoder) {
        map.put(encoderName, passwordEncoder);
    }

    public static PasswordEncoder getDefaultPasswordEncoder() {
        return getPasswordEncoder(DEFAULT_ENCODER_NAME);
    }

    public static PasswordEncoder getPasswordEncoder(String encoderName) {
        return map.get(encoderName);
    }
}
