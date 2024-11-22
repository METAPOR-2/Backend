package com.example.metapor.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SimpleResponse {
    @Schema(example = "200")
    private int status;
    @Schema(example = "success")
    private String message;

    public static SimpleResponse success() {
        return SimpleResponse.builder()
                .status(200)
                .message("success")
                .build();
    }
}