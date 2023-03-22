package com.momc.admin.infra.dev;

import com.momc.admin.application.service.MemberAppService;
import com.momc.admin.domain.admin.entity.Admin;
import com.momc.admin.domain.admin.entity.AdminRole;
import com.momc.admin.domain.admin.repository.AdminRepository;
import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.guild.entity.Guild;
import com.momc.admin.domain.guild.entity.GuildMemberGrade;
import com.momc.admin.domain.guild.repository.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Profile("dev")
public class InitDB {

    private final InitProperties initProperties;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final GuildRepository guildRepository;
    private final MemberAppService memberAppService;
    public static final String LINE_SEPARATOR = "=============";

    @Transactional
    public void init() {
        initAdmins();
        initGuild();
        initOthers();
//        updateForDev();
    }

    private void initAdmins() {
        Admin admin1 = createAdmin("daum123", "qwe", "샷건", AdminRole.DEVELOPER);
        admin1.approve();

        adminRepository.save(admin1);
    }

    private Admin createAdmin(String loginId, String password, String adminName, AdminRole adminRole) {
        Admin admin = Admin.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .adminName(adminName)
                .build();
        admin.changeAdminRole(adminRole);
        return admin;
    }

    private void initGuild() {
        List<String> guildNames = initProperties.getGuildNames();
        for (String guildName : guildNames) {
            Guild guild = new Guild(guildName, 20);
            guildRepository.save(guild);
        }
    }

    private void initOthers() {
        LocalDate now = LocalDate.now();
        memberAppService.registerNewMember("개발하다샷건침", now);
    }
}
