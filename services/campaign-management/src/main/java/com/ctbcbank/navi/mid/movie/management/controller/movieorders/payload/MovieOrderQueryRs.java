package com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieOrderQueryRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "電影訂單清單", description = "電影訂單清單")
    private List<MovieOrderInfo> movieOrderInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieOrderInfo {
        @NotNull
        @Positive
        @Schema(title = "電影訂單數量", description = "電影訂單數量")
        private BigInteger quantity;

        @NotNull
        @Positive
        @Schema(title = "電影訂單總價", description = "電影訂單總價")
        private BigInteger totalPrice;

        @NotNull
        @Schema(title = "用戶名稱", description = "用戶名稱")
        private String userName;

        @NotNull
        @Schema(title = "電影名稱", description = "電影名稱")
        private String movieName;
    }
}
