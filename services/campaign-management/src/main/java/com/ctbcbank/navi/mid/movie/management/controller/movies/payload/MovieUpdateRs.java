package com.ctbcbank.navi.mid.movie.management.controller.movies.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiRequestPayload;
import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieUpdateRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "電影資訊", description = "電影資訊")
    private MoviesInfo moviesInfo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoviesInfo{
        @NotBlank
        @Schema(title = "電影名稱", description = "電影名稱")
        private String movieName;

        @NotNull
        @Positive
        @Schema(title = "電影價格", description = "電影價格")
        private BigInteger price;
    }
}
