package com.momc.admin.infra.dev;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("dev")
@Component
@RequiredArgsConstructor
@Slf4j
public class Init {

    private final InitDB initDb;

    @PostConstruct
    public void init() {
        log.info(InitDB.LINE_SEPARATOR);
        log.info(InitDB.LINE_SEPARATOR);
        log.info("initiate start");

        initDb.init();

        log.info("initiate end");
        log.info(InitDB.LINE_SEPARATOR);
        log.info(InitDB.LINE_SEPARATOR);
    }


}
