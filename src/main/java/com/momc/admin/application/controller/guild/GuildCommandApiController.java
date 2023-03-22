package com.momc.admin.application.controller.guild;

import com.momc.admin.application.controller.common.ApiResponse;
import com.momc.admin.application.controller.common.ApiResponseFactory;
import com.momc.admin.application.service.GuildAppService;
import com.momc.admin.domain.guild.dto.GuildCreateForm;
import com.momc.admin.domain.guild.dto.GuildCreateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guilds")
@RequiredArgsConstructor
public class GuildCommandApiController {

    private final GuildAppService guildAppService;

    @PostMapping
    @ResponseBody
    public GuildCreateResult createNewGuild(@RequestBody GuildCreateForm form) {
        return guildAppService.createNewGuild(form.getGuildName(), form.getGuildLevel());
    }

    @PatchMapping("/{guildId}")
    @ResponseBody
    public GuildCreateResult modifyGuild(@PathVariable Integer guildId, @RequestBody GuildCreateForm form) {
        return guildAppService.changeGuildData(guildId, form.getGuildName(), form.getGuildLevel());
    }

    @PostMapping("/{guildId}/refresh")
    @ResponseBody
    public ApiResponse refresh(@PathVariable Integer guildId) {
        guildAppService.refresh(guildId);
        return ApiResponseFactory.ok();
    }
}
