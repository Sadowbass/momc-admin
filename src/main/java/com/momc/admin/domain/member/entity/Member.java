package com.momc.admin.domain.member.entity;

import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity<Member> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int expeditionLevel;

    @OneToOne(fetch = FetchType.LAZY)
    private Characters mainCharacter;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private LocalDate joinDate;

    private LocalDate leaveDate;

    @OneToMany(mappedBy = "member")
    private List<Characters> characters = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    public Member(LocalDate joinDate) {
        this.joinDate = joinDate;
        this.status = MemberStatus.JOIN;
    }

    public void changeExpeditionLevel(int expeditionLevel) {
        this.expeditionLevel = expeditionLevel;
    }

    public void changeMainCharacter(Characters mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public void changeMemberStatus(MemberStatus status) {
        this.status = status;
    }

    public void changeJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate != null ? joinDate : LocalDate.now();
    }

    public void changeLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
    }

    public void leaveMember(MemberStatus status, LocalDate leaveDate) {
        changeMemberStatus(status);
        changeLeaveDate(leaveDate);
    }
}
