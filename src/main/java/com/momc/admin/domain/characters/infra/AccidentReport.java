package com.momc.admin.domain.characters.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccidentReport {

    private String characterName;
    private int pageCount;
    private int postCount;
    private String url;
}
