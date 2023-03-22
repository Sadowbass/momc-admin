package com.momc.admin.application.controller.member.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MemberModifyForm {

    private Integer mainCharacterId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberModifyForm{");
        sb.append("mainCharacterId=").append(mainCharacterId);
        sb.append(", joinDate=").append(joinDate);
        sb.append('}');
        return sb.toString();
    }
}
