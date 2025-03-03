package com.ctbcbank.navi.mid.movie.management.controller.movies.payload;

import com.ibm.cbmp.fabric.web.api.message.ApiResponsePayload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieQueryRs extends ApiResponsePayload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "電影清單", description = "電影清單")
    private List<MoviesInfo> moviesInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoviesInfo {
        @NotBlank
        @Schema(title = "電影名稱", description = "電影名稱")
        private String movieName;

        @NotNull
        @Positive
        @Schema(title = "電影價格", description = "電影價格")
        private BigInteger price;
    }
}
