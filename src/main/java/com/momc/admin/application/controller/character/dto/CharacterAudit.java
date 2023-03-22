package com.momc.admin.application.controller.character.dto;

import com.momc.admin.domain.characters.infra.AccidentReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CharacterAudit {

    private String className;
    private int combatLevel;
    private double itemLevel;
    private List<AccidentReport> accidentReports;
}
