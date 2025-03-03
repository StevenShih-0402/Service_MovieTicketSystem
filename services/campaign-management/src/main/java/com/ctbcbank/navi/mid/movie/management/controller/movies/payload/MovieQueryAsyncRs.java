package com.ctbcbank.navi.mid.movie.management.controller.movies.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class MovieQueryAsyncRs extends ApiRequestPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Valid
    @Schema(title = "電影訂單清單", description = "電影訂單清單")
    private List<MovieOrderInfo> movieOrderInfoList;

    @Valid
    @Schema(title = "電影清單", description = "電影清單")
    private List<MovieInfo> movieInfoList;

    @Valid
    @Schema(title = "用戶清單", description = "用戶清單")
    private List<UserInfo> userInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieOrderInfo{

        @NotNull
        @Positive
        @Schema(title = "電影訂單 ID", description = "電影訂單 ID")
        private BigInteger movieOrderId;

        @NotNull
        @Positive
        @Schema(title = "電影訂單數量", description = "電影訂單數量")
        private BigInteger quantity;

        @NotNull
        @Positive
        @Schema(title = "電影訂單總價", description = "電影訂單總價")
        private BigInteger totalPrice;

        @NotNull
        @Schema(title = "用戶 ID", description = "用戶 ID")
        private BigInteger userId;

        @NotNull
        @Schema(title = "電影 ID", description = "電影 ID")
        private BigInteger movieId;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieInfo{

        @NotNull
        @Positive
        @Schema(title = "電影 ID", description = "電影 ID")
        private BigInteger movieId;

        @NotBlank
        @Schema(title = "電影名稱", description = "電影名稱")
        private String movieName;

        @NotNull
        @jakarta.validation.constraints.Positive
        @Schema(title = "電影價格", description = "電影價格")
        private BigInteger price;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo{

        @NotNull
        @Positive
        @Schema(title = "用戶 ID", description = "用戶 ID")
        private BigInteger userId;

        @NotBlank
        @Schema(title = "用戶名稱", description = "用戶名稱")
        private String userName;

        @NotBlank
        @Email
        @Schema(title = "用戶信箱", description = "用戶信箱")
        private String email;
    }
}
