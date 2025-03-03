package com.ctbcbank.navi.mid.movie.management.service.movies.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieUpdateRsBo {

    private MoviesInfoBo moviesInfoBo;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoviesInfoBo {
        private String movieName;
        private BigInteger price;
    }
}
