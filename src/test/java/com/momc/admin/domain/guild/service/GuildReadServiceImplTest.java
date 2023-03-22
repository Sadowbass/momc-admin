package com.momc.admin.domain.guild.service;

import com.momc.admin.application.service.GuildAppService;
import com.momc.admin.domain.guild.repository.GuildRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class GuildReadServiceImplTest {

    @Autowired
    GuildReadService guildReadService;
    @Autowired
    GuildRepository guildRepository;
    @Autowired
    GuildAppService guildAppService;

    @Test
    @Transactional
    void testGetGuildInfo() {
        guildAppService.refresh(1);

    }
}