package com.momc.admin.domain.guild.entity;

import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guild extends BaseEntity<Guild> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String guildName;

    private int guildLevel;

    private int maxMemberCapacity;

    @OneToMany(mappedBy = "guild")
    private List<GuildCharacter> guildCharacters = new ArrayList<>();

    public Guild(String guildName, int guildLevel) {
        this.guildName = guildName;
        this.guildLevel = guildLevel;
        this.maxMemberCapacity = changeMaxMemberCapacity(guildLevel);
    }

    public void changeGuildName(String guildName) {
        this.guildName = guildName;
    }

    public GuildCharacter addNewGuildCharacter(Characters characters, GuildMemberGrade grade) {
        GuildCharacter guildCharacter = GuildCharacter.builder()
                .guild(this)
                .characters(characters)
                .guildMemberGrade(grade)
                .build();

        this.guildCharacters.add(guildCharacter);
        guildCharacter.changeGuild(this);

        return guildCharacter;
    }

    public void deleteGuildCharacter(GuildCharacter guildCharacter) {
        this.guildCharacters.remove(guildCharacter);
        guildCharacter.changeGuild(null);
    }

    public void changeGuildLevel(int guildLevel) {
        if (guildLevel < 1) {
            guildLevel = 1;
        }

        this.guildLevel = guildLevel;
        this.maxMemberCapacity = changeMaxMemberCapacity(guildLevel);
    }

    private int changeMaxMemberCapacity(int guildLevel) {
        return IntStream.range(2, guildLevel + 1)
                .reduce(30, this::calculateCapacity);
    }

    private int calculateCapacity(int total, int eachGuildLevel) {
        if (eachGuildLevel < 12) {
            return total + 2;
        } else if (eachGuildLevel < 22) {
            return total + 3;
        } else {
            return total + 5;
        }
    }
}
