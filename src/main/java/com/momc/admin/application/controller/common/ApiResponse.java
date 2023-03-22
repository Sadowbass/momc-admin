package com.momc.admin.application.controller.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ApiResponse {

    private int rspCode;
    private String rspMessage;
    private Object result;
}
