package com.emazon.shopping_service.domain.util.pageable;

import java.util.List;

public class CustomPage<T>{

    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
    private Integer currentPage;
    private  Boolean ascending;
    private Boolean empty;

    public CustomPage(List<T> content, Long totalElements, Integer totalPages, Integer currentPage, Boolean ascending, Boolean empty) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.ascending = ascending;
        this.empty = empty;
    }

    public CustomPage() {
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(Boolean ascending) {
        this.ascending = ascending;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }
}
