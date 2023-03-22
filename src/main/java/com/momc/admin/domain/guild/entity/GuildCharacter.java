package com.momc.admin.domain.guild.entity;

import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class GuildCharacter extends BaseEntity<GuildCharacter> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Guild guild;

    @OneToOne(fetch = FetchType.LAZY)
    private Characters characters;

    @Enumerated(EnumType.STRING)
    private GuildMemberGrade guildMemberGrade;

    @Builder
    public GuildCharacter(Guild guild, Characters characters, GuildMemberGrade guildMemberGrade) {
        this.guild = guild;
        this.characters = characters;
        this.guildMemberGrade = guildMemberGrade;
    }

    public void changeGuild(Guild guild) {
        this.guild = guild;
    }

    public void changeGuildMemberGrade(GuildMemberGrade guildMemberGrade) {
        this.guildMemberGrade = guildMemberGrade;
    }
}
