package com.momc.admin.application.controller.guild;

import com.momc.admin.domain.guild.dto.GuildInfoDto;
import com.momc.admin.domain.guild.service.GuildReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/guilds")
@RequiredArgsConstructor
public class GuildController {

    private final GuildReadService guildReadService;

    @GetMapping
    public String sendGuildListView(Model model) {
        List<GuildInfoDto> allGuildInfo = guildReadService.getAllGuildInfo();
        model.addAttribute("guilds", allGuildInfo);

        return "guild/guild-list";
    }

    @GetMapping("/{guildId}")
    public String sendGuildDetailView(@PathVariable Integer guildId, Model model) {
        GuildInfoDto guildInfo = guildReadService.getGuildInfo(guildId);
        model.addAttribute("guild", guildInfo);

        return "guild/guild-detail";
    }
}
