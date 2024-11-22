package com.example.metapor.common.response;

import org.springframework.data.domain.Page;

public record PageMetaData (
        int totalPage,
        int currentPage,
        int totalElement,
        int pageSize,
        boolean hasNext
){
    public static PageMetaData extract(Page<?> page) {
        return new PageMetaData(
                page.getTotalPages(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.getSize(),
                page.hasNext());
    }
}
