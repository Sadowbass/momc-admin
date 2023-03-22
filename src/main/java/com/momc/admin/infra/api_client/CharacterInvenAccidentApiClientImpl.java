package com.momc.admin.infra.api_client;

import com.momc.admin.domain.characters.infra.AccidentReport;
import com.momc.admin.domain.characters.infra.CharacterInvenAccidentApiClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CharacterInvenAccidentApiClientImpl implements CharacterInvenAccidentApiClient {

    @Override
    public List<AccidentReport> searchAllAccidentPosts(List<String> characterNames) {
        return characterNames.stream()
                .parallel()
                .map(this::searchAccidentPosts)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public AccidentReport searchAccidentPosts(String characterName) {
        try {
            String baseUrl = "https://www.inven.co.kr/board/lostark/5355?query=list&p=1&category=%EC%95%84%EB%A7%8C&sterm=&name=subjcont&keyword=";
            String encodedCharName = URLEncoder.encode(characterName, StandardCharsets.UTF_8);
            URI uri = URI.create(baseUrl + encodedCharName);

            Connection connect = Jsoup.connect(uri.toString());
            Document document = connect.get();
            Element body = document.body();
            Elements noResult = body.getElementsByClass("no-result");

            if (doesAccident(noResult)) {
                return calcAccidentReport(characterName, body, uri.toString());
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean doesAccident(Elements noResult) {
        return noResult.size() != 1;
    }

    AccidentReport calcAccidentReport(String characterName, Element body, String uri) {
        Element paging = body.getElementById("paging");
        int numberOfPage = paging.childrenSize() - 2;
        if (numberOfPage > 1) {
            return new AccidentReport(characterName, numberOfPage, 0, uri);
        } else {
            Element tableBody = body.getElementsByClass("notice all").get(0).parent();
            int numberOfPost = tableBody.childrenSize() - 1;
            return new AccidentReport(characterName, numberOfPage, numberOfPost, uri);
        }
    }
}
