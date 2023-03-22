package com.momc.admin.domain.characters.service;

import com.momc.admin.domain.characters.dto.CharacterDetailData;
import com.momc.admin.domain.characters.repository.CharactersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterReadServiceImpl implements CharacterReadService {

    private final CharactersRepository charactersRepository;

    @Override
    public List<CharacterDetailData> getMembersAllCharacters(Integer memberId) {
        return charactersRepository.findCharacterDetailByMemberId(memberId);
    }

    @Override
    public List<Integer> getMemberIdsByCharacterNameLike(String characterName) {
        return charactersRepository.findMemberIdsByCharacterNameLike(characterName);
    }

    @Override
    public List<String> getAllCharacterNames() {
        return charactersRepository.findAllCharacterNames();
    }
}
