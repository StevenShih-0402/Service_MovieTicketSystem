package com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload;

import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieUpdateRq;
import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieOrderUpdateRq extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(title = "電影訂單編號", description = "電影訂單編號")
    private BigInteger id;

    @Valid
    @Schema(title = "電影訂單資訊", description = "電影訂單資訊")
    private MovieOrdersInfo movieOrdersInfo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieOrdersInfo {
        @NotNull
        @Positive
        @Schema(title = "電影訂單數量", description = "電影訂單數量")
        private BigInteger quantity;
    }
}
