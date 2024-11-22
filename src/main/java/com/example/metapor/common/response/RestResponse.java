package com.example.metapor.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {

    @Schema(example = "200")
    private int status;
    @Schema(example = "success")
    private String message;
    private T data;

    /**
     * 200 OK
     */
    public static<T> RestResponse<T> ok(final T data) {
        return RestResponse.<T>builder()
                .status(200)
                .message("success")
                .data(data).build();
    }

    public static<T> ResponseEntity<RestResponse<T>> generateResponse(T data) {
        return ResponseEntity.ok(RestResponse.<T>builder()
                .status(200)
                .message("success")
                .data(data).build());
    }
}

