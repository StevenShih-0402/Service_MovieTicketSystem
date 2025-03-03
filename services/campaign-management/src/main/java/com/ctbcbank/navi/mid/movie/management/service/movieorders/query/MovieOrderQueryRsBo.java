package com.ctbcbank.navi.mid.movie.management.service.movieorders.query;

import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderQueryRs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieOrderQueryRsBo {

    private List<MovieOrderInfoBo> movieOrderInfoBo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieOrderInfoBo {
        private BigInteger quantity;
        private String userName;
        private String movieName;
        private BigInteger totalPrice;
    }
}
