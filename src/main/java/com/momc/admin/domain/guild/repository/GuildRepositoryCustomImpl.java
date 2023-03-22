package com.momc.admin.domain.guild.repository;

import com.momc.admin.domain.characters.entity.QCharacters;
import com.momc.admin.domain.guild.dto.GuildInfoDto;
import com.momc.admin.domain.guild.dto.QGuildCharacterDto;
import com.momc.admin.domain.guild.dto.QGuildInfoDto;
import com.momc.admin.domain.guild.entity.Guild;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.momc.admin.domain.characters.entity.QCharacters.characters;
import static com.momc.admin.domain.guild.entity.QGuild.guild;
import static com.momc.admin.domain.guild.entity.QGuildCharacter.guildCharacter;
import static com.momc.admin.domain.member.entity.QMember.member;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

public class GuildRepositoryCustomImpl implements GuildRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public GuildRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<GuildInfoDto> findAllGuildInfoDto() {
        return getGuildInfo(null);
    }

    @Override
    public GuildInfoDto findGuildInfoDto(Integer guildId) {
        List<GuildInfoDto> transform = getGuildInfo(guildId);
        return transform.isEmpty() ? GuildInfoDto.builder().build() : transform.get(0);
    }

    @Override
    public Optional<Guild> findByIdFetchAll(Integer guildId) {
        QCharacters mainCharacter = new QCharacters("mainCharacter");
        Guild result = queryFactory
                .select(guild)
                .from(guild)
                .leftJoin(guild.guildCharacters, guildCharacter)
                .fetchJoin()
                .leftJoin(guildCharacter.characters, characters)
                .fetchJoin()
                .leftJoin(characters.member, member)
                .fetchJoin()
                .leftJoin(member.mainCharacter, mainCharacter)
                .fetchJoin()
                .where(guildIdEq(guildId))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    private List<GuildInfoDto> getGuildInfo(Integer guildId) {
        QCharacters mainCharacter = new QCharacters("mainCharacter");
        return queryFactory
                .from(guild)
                .leftJoin(guild.guildCharacters, guildCharacter)
                .leftJoin(guildCharacter.characters, characters)
                .leftJoin(characters.member, member)
                .leftJoin(member.mainCharacter, mainCharacter)
                .where(guildIdEq(guildId))
                .transform(
                        groupBy(guild.id)
                                .list(
                                        new QGuildInfoDto(
                                                guild.id,
                                                guild.guildName,
                                                guild.guildLevel,
                                                guild.maxMemberCapacity,
                                                list(
                                                        new QGuildCharacterDto(
                                                                guildCharacter.id,
                                                                member.id,
                                                                characters.id,
                                                                mainCharacter.characterName,
                                                                characters.characterName,
                                                                characters.itemLevel,
                                                                characters.className,
                                                                guildCharacter.guildMemberGrade
                                                        ).skipNulls()
                                                )
                                        )
                                )
                );
    }

    private BooleanExpression guildIdEq(Integer guildId) {
        return guildId == null ? null : guild.id.eq(guildId);
    }
}
