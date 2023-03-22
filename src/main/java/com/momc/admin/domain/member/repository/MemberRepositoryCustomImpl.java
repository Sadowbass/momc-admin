package com.momc.admin.domain.member.repository;

import com.momc.admin.domain.characters.entity.QCharacters;
import com.momc.admin.domain.member.dto.*;
import com.momc.admin.domain.member.entity.MemberStatus;
import com.momc.admin.utils.PageObject;
import com.momc.admin.utils.PageRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

import static com.momc.admin.domain.characters.entity.QCharacters.characters;
import static com.momc.admin.domain.member.entity.QComment.comment;
import static com.momc.admin.domain.member.entity.QMember.member;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public PageObject<MemberInfo> findAllJoinedMembers(PageRequest pageRequest) {
        BooleanExpression expression = member.status.eq(MemberStatus.JOIN);
        return findAllByWhereExp(expression, pageRequest);
    }

    @Override
    public PageObject<MemberInfo> findAllLeftMembers(PageRequest pageRequest) {
        BooleanExpression expression = member.status.in(MemberStatus.LEAVE, MemberStatus.BANNED);
        return findAllByWhereExp(expression, pageRequest);
    }

    @Override
    public PageObject<MemberInfo> findMembersByIdIn(PageRequest pageRequest, Collection<Integer> ids) {
        BooleanExpression expression = member.id.in(ids);
        return findAllByWhereExp(expression, pageRequest);
    }

    private PageObject<MemberInfo> findAllByWhereExp(BooleanExpression exp, PageRequest pageRequest) {
        List<MemberInfo> result = queryFactory
                .select(
                        new QMemberInfo(
                                member.id,
                                member.expeditionLevel,
                                characters.characterName,
                                member.status,
                                member.characters.size(),
                                member.comments.size()
                        )
                )
                .from(member)
                .join(member.mainCharacter, characters)
                .where(exp)
                .limit(pageRequest.getLimit())
                .offset(pageRequest.getOffset())
                .fetch();

        return new PageObject<>(result, pageRequest.getPage(), getCount(exp)::fetchFirst);
    }

    private JPAQuery<Long> getCount(BooleanExpression exp) {
        return queryFactory
                .select(member.count())
                .from(member)
                .where(exp);
    }

    @Override
    public MemberInfoDetail findMemberInfoDetailByMemberId(Integer memberId) {
        QCharacters mainCharacter = new QCharacters("mainCharacter");

        List<MemberInfoDetail> result = queryFactory
                .from(member)
                .join(member.mainCharacter, mainCharacter)
                .leftJoin(member.comments, comment)
                .where(member.id.eq(memberId))
                .transform(
                        groupBy(member.id).list(
                                new QMemberInfoDetail(
                                        member.id,
                                        member.expeditionLevel,
                                        member.mainCharacter.characterName,
                                        member.status,
                                        member.joinDate,
                                        member.leaveDate,
                                        list(
                                                new QCommentDto(
                                                        comment.id,
                                                        comment.member.id,
                                                        comment.commentCategory,
                                                        comment.content,
                                                        comment.createdBy,
                                                        comment.createdDate
                                                ).skipNulls()
                                        )
                                )
                        )
                );

        if (ObjectUtils.isEmpty(result)) {
            //TODO create new exception
            throw new RuntimeException("no member find");
        }

        return result.get(0);
    }
}
