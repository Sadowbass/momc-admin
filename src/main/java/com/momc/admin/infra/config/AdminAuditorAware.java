package com.momc.admin.infra.config;

import com.momc.admin.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminAuditorAware implements AuditorAware<String> {

    @Value("${spring.profiles.active}")
    private String currentEnv;

    @Override
    public Optional<String> getCurrentAuditor() {
        if (validEnvironmentDevOrTest()) {
            return Optional.of("dev");
        }

        String adminName = SecurityUtils.getAdminName();
        return Optional.of(adminName);
    }

    private boolean validEnvironmentDevOrTest() {
        return "dev".equalsIgnoreCase(currentEnv) || "test".equalsIgnoreCase(currentEnv);
    }
}
