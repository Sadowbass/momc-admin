package com.momc.admin.domain.guild.repository;

import com.momc.admin.domain.guild.entity.GuildCharacter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.momc.admin.domain.guild.entity.QGuildCharacter.guildCharacter;

public class GuildCharacterRepositoryCustomImpl implements GuildCharacterRepositoryCustom {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JPAQueryFactory queryFactory;

    public GuildCharacterRepositoryCustomImpl(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void bulkUpdate(List<GuildCharacter> guildCharacterList) {
        String sql = "update guild_character " +
                "set guild_member_grade = :guildMemberGrade, last_modified_date = :lastModifiedDate " +
                "where characters_id = :characterId";

        SqlParameterSource[] params = guildCharacterList.stream()
                .map(gc -> new MapSqlParameterSource()
                        .addValue("guildMemberGrade", gc.getGuildMemberGrade().name())
                        .addValue("lastModifiedDate", LocalDateTime.now())
                        .addValue("characterId", gc.getCharacters().getId())
                )
                .toArray(SqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(sql, params);
    }

    @Override
    public void bulkDelete(List<GuildCharacter> guildCharacters) {
        List<Integer> ids = guildCharacters.stream().map(GuildCharacter::getId).collect(Collectors.toList());
        queryFactory
                .delete(guildCharacter)
                .where(guildCharacter.id.in(ids))
                .execute();
    }
}
