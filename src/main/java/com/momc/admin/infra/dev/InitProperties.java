package com.momc.admin.infra.dev;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("dev")
@ConstructorBinding
@ConfigurationProperties("momc.init")
@Getter
public class InitProperties {

    private final List<String> guildNames;

    public InitProperties(List<String> guildNames) {
        this.guildNames = guildNames;
    }
}
