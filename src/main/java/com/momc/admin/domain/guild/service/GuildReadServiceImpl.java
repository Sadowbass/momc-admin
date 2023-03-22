package com.momc.admin.domain.guild.service;

import com.momc.admin.domain.guild.dto.GuildCharacterDto;
import com.momc.admin.domain.guild.dto.GuildInfoDto;
import com.momc.admin.domain.guild.entity.Guild;
import com.momc.admin.domain.guild.entity.GuildCharacter;
import com.momc.admin.domain.guild.repository.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuildReadServiceImpl implements GuildReadService {

    private final GuildRepository guildRepository;

    @Override
    public List<GuildInfoDto> getAllGuildInfo() {
        return guildRepository.findAllGuildInfoDto();
    }

    @Override
    public GuildInfoDto getGuildInfo(Integer guildId) {
        GuildInfoDto guildInfoDto = guildRepository.findGuildInfoDto(guildId);
        List<GuildCharacterDto> guildCharacters = guildInfoDto.getGuildCharacters();
        guildCharacters.sort(GuildCharacterDto::comparator);

        return guildInfoDto;
    }

    @Override
    public GuildInfoDto getGuildInfoForTransaction(Integer guildId) {
        Optional<Guild> result = guildRepository.findByIdFetchAll(guildId);
        return convertToGuildInfoDto(result.orElseThrow());
    }

    private GuildInfoDto convertToGuildInfoDto(Guild guild) {
        List<GuildCharacterDto> guildCharacterDtos = guild.getGuildCharacters()
                .stream()
                .map(this::convertToGuildCharacterDto)
                .collect(Collectors.toList());

        return GuildInfoDto.builder()
                .id(guild.getId())
                .guildName(guild.getGuildName())
                .guildLevel(guild.getGuildLevel())
                .maxMemberCapacity(guild.getMaxMemberCapacity())
                .guildCharacters(guildCharacterDtos)
                .build();
    }

    private GuildCharacterDto convertToGuildCharacterDto(GuildCharacter guildCharacter) {
        return GuildCharacterDto.builder()
                .id(guildCharacter.getId())
                .memberId(guildCharacter.getCharacters().getMember().getId())
                .characterId(guildCharacter.getCharacters().getId())
                .mainCharacterName(guildCharacter.getCharacters().getMember().getMainCharacter().getCharacterName())
                .characterName(guildCharacter.getCharacters().getCharacterName())
                .itemLevel(guildCharacter.getCharacters().getItemLevel())
                .className(guildCharacter.getCharacters().getClassName())
                .guildMemberGrade(guildCharacter.getGuildMemberGrade())
                .build();
    }
}
