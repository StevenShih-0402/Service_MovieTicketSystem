package com.ctbcbank.navi.mid.movie.management.service.movies.create;

import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieCreateRq;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieCreateRs;

public class MoviesCreateConverter {
    public static MovieCreateRqBo parseToBo(MovieCreateRq movieCreateRq){
        MovieCreateRqBo movieCreateRqBo = new MovieCreateRqBo();

        movieCreateRqBo.setMovieName(movieCreateRq.getMovieName());
        movieCreateRqBo.setPrice(movieCreateRq.getPrice());

        return movieCreateRqBo;
    }

    public static MovieCreateRs parseToRs(MovieCreateRsBo movieCreateRsBo){
        MovieCreateRs movieCreateRs = new MovieCreateRs();

        movieCreateRs.setId(movieCreateRsBo.getId());
        movieCreateRs.setMovieName(movieCreateRsBo.getMovieName());

        return movieCreateRs;
    }
}
