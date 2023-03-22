package com.momc.admin.domain.characters.infra;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class CharacterInvenAccidentApiClientImplTest {

    static class AccidentReport {

        private String characterName;
        private int pageCount;
        private int postCount;

        public AccidentReport(String characterName, int pageCount, int postCount) {
            this.characterName = characterName;
            this.pageCount = pageCount;
            this.postCount = postCount;
        }
    }

    @Test
    void testJsoup() {
        String charName1 = "소주코더";
        String charName2 = "개발하다샷건침";
        String charName3 = "비와서개발안함";
        String charName4 = "개발못함";
        String charName5 = "자바못함";
        String charName6 = "스프링코틀린";
        String charName7 = "뒤에서도개발안함";
        String charName8 = "개발자아님";

        List<String> charNames = List.of(charName1, charName2, charName3, charName4, charName5, charName6, charName7, charName8);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<AccidentReport> result = charNames.stream()
                .parallel()
                .map(this::logic)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println("result.size() = " + result.size());
    }

    AccidentReport logic(String charName) {
        try {
            String baseUrl = "https://www.inven.co.kr/board/lostark/5355?query=list&p=1&category=%EC%95%84%EB%A7%8C&sterm=&name=subjcont&keyword=";
            String encodedCharname1 = URLEncoder.encode(charName, StandardCharsets.UTF_8);
            URI uri = URI.create(baseUrl + encodedCharname1);

            Connection connect = Jsoup.connect(uri.toString());
            Document document = connect.get();
            Element body = document.body();
            Elements noResult = body.getElementsByClass("no-result");

            if (doesAccident(noResult)) {
                return calcAccidentReport(charName, body);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean doesAccident(Elements noResult) {
        return !(noResult.size() == 1);
    }

    AccidentReport calcAccidentReport(String characterName, Element body) {
        Element paging = body.getElementById("paging");
        int numberOfPage = paging.childrenSize() - 2;
        if (numberOfPage > 1) {
            return new AccidentReport(characterName, numberOfPage, 0);
        } else {
            Element tableBody = body.getElementsByClass("notice all").get(0).parent();
            int numberOfPost = tableBody.childrenSize() - 1;
            return new AccidentReport(characterName, 1, numberOfPost);
        }
    }
}