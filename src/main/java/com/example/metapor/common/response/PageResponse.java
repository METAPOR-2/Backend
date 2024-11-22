package com.example.metapor.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T>{
    @Schema(example = "200")
    private int status;
    @Schema(example = "success")
    private String message;
    private int totalPage;
    private int currentPage;
    private int totalElement;
    private int pageSize;
    private boolean hasNext;
    private List<T> data;


    public static<T> PageResponse<T> ok(PageMetaData pageMetaData, List<T> data) {
        return PageResponse.<T>builder()
                .status(200)
                .message("success")
                .data(data)
                .totalPage(pageMetaData.totalPage())
                .currentPage(pageMetaData.currentPage() + 1)
                .totalElement(pageMetaData.totalElement())
                .pageSize(pageMetaData.pageSize())
                .hasNext(pageMetaData.hasNext())
                .build();
    }
}
