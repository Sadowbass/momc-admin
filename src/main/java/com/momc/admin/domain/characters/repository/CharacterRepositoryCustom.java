package com.momc.admin.domain.characters.repository;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.characters.entity.Characters;

import java.util.List;

public interface CharacterRepositoryCustom {

    void saveAllBulkInsert(List<Characters> list);

    List<CharacterDetailData> findCharacterDetailByMemberId(Integer memberId);
}
