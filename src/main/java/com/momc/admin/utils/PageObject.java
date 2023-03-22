package com.momc.admin.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;

@Getter
public class PageObject<T> {

    public static final int DEFAULT_PAGES_PER_BLOCK = 5;
    public static final int DEFAULT_CONTENTS_PER_PAGE = 20;

    private List<T> contents;
    private long totalNumberOfContents;

    private int pagesPerBlock;
    private int contentsPerPage;

    private int currentPage;
    private int lastPage;

    private List<Integer> shownPages;

    public PageObject(List<T> contents, int currentPage, LongSupplier totalSupplier) {
        this(contents, currentPage, DEFAULT_PAGES_PER_BLOCK, DEFAULT_CONTENTS_PER_PAGE, totalSupplier);
    }

    public PageObject(List<T> contents, int currentPage, int pagesPerBlock, int contentsPerPage, LongSupplier totalSupplier) {
        this.contents = contents != null ? contents : List.of();
        this.currentPage = Math.max(currentPage, 1);
        this.pagesPerBlock = Math.max(pagesPerBlock, 1);
        this.contentsPerPage = Math.max(contentsPerPage, 1);

        if (this.contentsPerPage > this.contents.size()) {
            this.totalNumberOfContents = this.contents.size();
        } else {
            this.totalNumberOfContents = totalSupplier.getAsLong();
        }

        init();
    }

    private void init() {
        this.lastPage = calcLastPage();
        this.shownPages = createShownPages();
    }

    private int calcLastPage() {
        int temp = (int) Math.ceil((double) this.totalNumberOfContents / this.contentsPerPage);
        return temp != 0 ? temp : 1;
    }

    private List<Integer> createShownPages() {
        this.shownPages = new ArrayList<>();

        int startPage = (calcPageBlock(currentPage) - 1) * pagesPerBlock + 1;
        int endPage = Math.min(calcPageBlock(currentPage) * pagesPerBlock, lastPage);

        for (int i = startPage; i <= endPage; i++) {
            shownPages.add(i);
        }

        return shownPages;
    }

    public boolean isNeedPrevButton() {
        return calcPageBlock(currentPage) > 1;
    }

    public int getPrevButtonPage() {
        return (calcPageBlock(currentPage) - 1) * pagesPerBlock;
    }

    public boolean isNeedNextButton() {
        return calcPageBlock(currentPage) < calcPageBlock(lastPage);
    }

    public int getNextButtonPage() {
        return calcPageBlock(currentPage) * pagesPerBlock + 1;
    }

    public int calcPageBlock(int pageNumber) {
        return (pageNumber / (pagesPerBlock + 1)) + 1;
    }
}
