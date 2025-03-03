package com.ctbcbank.navi.mid.movie.management.service.movies.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovieQueryRsBo {

    private List<MoviesInfoBo> moviesInfoList;

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoviesInfoBo{
        private String movieName;
        private BigInteger price;
    }
}
