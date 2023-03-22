package com.momc.admin.domain.characters.repository;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.characters.dto.QCharacterDetailData;
import com.momc.admin.domain.characters.entity.Characters;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.persistence.EntityManager;
import java.util.List;

import static com.momc.admin.domain.characters.entity.QCharacters.characters;
import static com.momc.admin.domain.guild.entity.QGuild.guild;
import static com.momc.admin.domain.guild.entity.QGuildCharacter.guildCharacter;
import static com.momc.admin.domain.member.entity.QMember.member;

public class CharacterRepositoryCustomImpl implements CharacterRepositoryCustom {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JPAQueryFactory queryFactory;

    public CharacterRepositoryCustomImpl(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void saveAllBulkInsert(List<Characters> list) {
        String sql = "insert into characters " +
                "(member_id, character_name, class_name, combat_level, item_level, created_date, last_modified_date) " +
                "values (:member.id, :characterName, :className, :combatLevel, :itemLevel, :createdDate, :lastModifiedDate)";

        SqlParameterSource[] params = list.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(sql, params);
    }

    @Override
    public List<CharacterDetailData> findCharacterDetailByMemberId(Integer memberId) {
        return queryFactory
                .select(
                        new QCharacterDetailData(
                                member.id,
                                characters.id,
                                member.expeditionLevel,
                                characters.characterName,
                                characters.className,
                                characters.combatLevel,
                                characters.itemLevel,
                                guild.guildName,
                                guildCharacter.guildMemberGrade.stringValue()
                        )
                )
                .from(member)
                .join(member.characters, characters)
                .leftJoin(guildCharacter)
                .on(guildCharacter.characters.eq(characters))
                .leftJoin(guildCharacter.guild, guild)
                .where(member.id.eq(memberId))
                .fetch();
    }
}
