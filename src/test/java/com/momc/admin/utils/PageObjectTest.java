package com.momc.admin.utils;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PageObjectTest {

    @Test
    void testPageObject() {
        /**
         * 페이지 시작은 1페이지부터
         * 블럭 시작은 1부터
         * 총 135건
         * 페이지당 10건
         * 한 블럭당 5페이지
         * 현재페이지 7페이지
         */

        //given
        List<String> contents = new ArrayList<>();
        EasyRandom easyRandom = new EasyRandom();
        for (int i = 0; i < 10; i++) {
            contents.add(easyRandom.nextObject(String.class));
        }

        //when
        PageObject<String> po = new PageObject<>(contents, 135, 7, 5, () -> 10);

        //then
        assertThat(po.getLastPage()).isEqualTo(14);
        assertThat(po.isNeedPrevButton()).isTrue();
        assertThat(po.isNeedNextButton()).isTrue();
        assertThat(po.getPrevButtonPage()).isEqualTo(5);
        assertThat(po.getNextButtonPage()).isEqualTo(11);
        assertThat(po.getShownPages()).containsOnly(6, 7, 8, 9, 10);
        assertThat(po.getContents()).hasSize(10);
    }
}