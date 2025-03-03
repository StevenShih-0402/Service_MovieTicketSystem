package com.ctbcbank.navi.mid.movie.management.service.movieorders.create;

import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderCreateRq;
import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderCreateRs;

public class MovieOrdersCreateConverter {
    public static MovieOrderCreateRqBo parseToBo(MovieOrderCreateRq movieOrderCreateRq){
        MovieOrderCreateRqBo movieOrderCreateRqBo = new MovieOrderCreateRqBo();

        movieOrderCreateRqBo.setMovieId(movieOrderCreateRq.getMovieId());
        movieOrderCreateRqBo.setUserId(movieOrderCreateRq.getUserId());
        movieOrderCreateRqBo.setQuantity(movieOrderCreateRq.getQuantity());

        return movieOrderCreateRqBo;
    }

    public static MovieOrderCreateRs parseToRs(MovieOrderCreateRsBo movieOrderCreateRsBo){
        MovieOrderCreateRs movieOrderCreateRs = new MovieOrderCreateRs();

        movieOrderCreateRs.setId(movieOrderCreateRsBo.getId());
        movieOrderCreateRs.setTotalPrice(movieOrderCreateRsBo.getTotalPrice());

        return movieOrderCreateRs;
    }
}
