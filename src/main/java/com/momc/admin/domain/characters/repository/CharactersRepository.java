package com.momc.admin.domain.characters.repository;

import com.momc.admin.domain.characters.entity.Characters;
import com.momc.admin.domain.common.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CharactersRepository extends CharacterRepositoryCustom, BaseRepository<Characters, Integer>, JpaRepository<Characters, Integer> {

    @Override
    default Characters getEntityByPkElseThrow(Integer id) {
        //TODO create new exception
        return findById(id).orElseThrow(() -> new RuntimeException(String.format("cannot find entity by pk : %s", id)));
    }

    @Query("select c from Characters c join c.member m where m.id = :memberId")
    List<Characters> findAllByMemberId(@Param("memberId") Integer memberId);

    List<Characters> findByCharacterNameIn(List<String> characterNames);

    List<Characters> findAllByCharacterNameIn(Collection<String> characterName);

    @Query("select c.member.id from Characters c join c.member where c.characterName like %:characterName%")
    List<Integer> findMemberIdsByCharacterNameLike(@Param("characterName") String characterName);

    @Query("select c.characterName from Characters c order by c.characterName")
    List<String> findAllCharacterNames();
}
