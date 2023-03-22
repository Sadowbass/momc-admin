package com.momc.admin.domain.member.service;

import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.characters.repository.CharactersRepository;
import com.momc.admin.domain.member.entity.Comment;
import com.momc.admin.domain.member.entity.CommentCategory;
import com.momc.admin.domain.member.entity.Member;
import com.momc.admin.domain.member.repository.CommentRepository;
import com.momc.admin.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@SpringBootTest
class MemberReadServiceImplTest {

    @Autowired
    private MemberReadService service;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CharactersRepository charactersRepository;
    @Autowired
    private CommentRepository commentRepository;
    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void setUp() {
        Member member = new Member(LocalDate.now());
        Characters c1 = new Characters(member, "개발하다샷건침", "건슬링어", 60, 1546.7);
        Characters c2 = new Characters(member, "소주코더", "기공사", 60, 1470.0);
        Characters c3 = new Characters(member, "개발못함", "소서리스", 60, 1460.0);
        Characters c4 = new Characters(member, "비와서개발안함", "기상술사", 60, 1460.0);
        member.changeExpeditionLevel(160);
        member.changeMainCharacter(c1);

        Comment com1 = new Comment(member, CommentCategory.COMMENT, "코멘트1");
        Comment com2 = new Comment(member, CommentCategory.COMMENT, "코멘트2");

        memberRepository.save(member);
        charactersRepository.save(c1);
        charactersRepository.save(c2);
        charactersRepository.save(c3);
        charactersRepository.save(c4);
//        commentRepository.save(com1);
//        commentRepository.save(com2);

        em.flush();
        em.clear();
    }
}