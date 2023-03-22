package com.momc.admin.domain.characters.infra;

import java.util.List;

public interface CharacterInvenAccidentApiClient {

    List<AccidentReport> searchAllAccidentPosts(List<String> characterNames);
}
