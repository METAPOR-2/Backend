package com.example.metapor.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResponse<T> {

    @Schema(example = "200")
    private int status;
    @Schema(example = "success")
    private String message;
    private List<T> data;

    /**
     * 200 OK
     */
    public static<T> ListResponse<T> ok(final List<T> data) {
        return ListResponse.<T>builder()
                .status(200)
                .message("success")
                .data(data).build();
    }

    public static<T> ResponseEntity<ListResponse<T>> generateResponse(List<T> data) {
        return ResponseEntity.ok(ListResponse.<T>builder()
                .status(200)
                .message("success")
                .data(data).build());
    }
}

