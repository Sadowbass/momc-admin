package com.momc.admin.domain.characters.entity;

import com.momc.admin.domain.common.entity.BaseEntity;
import com.momc.admin.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Characters extends BaseEntity<Characters> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String characterName;
    private String className;
    private int combatLevel;
    private Double itemLevel;

    @Builder
    public Characters(Member member, String characterName, String className, int combatLevel, Double itemLevel) {
        this.member = member;
        this.characterName = characterName;
        this.className = className;
        this.combatLevel = combatLevel;
        this.itemLevel = itemLevel;
    }

    public boolean hasAnyChange(String className, int combatLevel, Double itemLevel) {
        return !(this.className.equalsIgnoreCase(className) && this.combatLevel == combatLevel && this.itemLevel.equals(itemLevel));
    }

    public void updateCharacter(String className, int combatLevel, Double itemLevel) {
        this.className = className;
        this.combatLevel = combatLevel;
        this.itemLevel = itemLevel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Characters{");
        sb.append("id=").append(id);
        sb.append(", member=").append(member);
        sb.append(", characterName='").append(characterName).append('\'');
        sb.append(", className='").append(className).append('\'');
        sb.append(", combatLevel=").append(combatLevel);
        sb.append(", itemLevel=").append(itemLevel);
        sb.append('}');
        return sb.toString();
    }
}
