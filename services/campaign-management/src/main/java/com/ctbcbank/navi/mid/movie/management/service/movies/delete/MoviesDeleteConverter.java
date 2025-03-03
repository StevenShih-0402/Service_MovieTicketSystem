package com.ctbcbank.navi.mid.movie.management.service.movies.delete;

import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieDeleteRq;

public class MoviesDeleteConverter {
    public static MovieDeleteRqBo parseToBo(MovieDeleteRq moviesDeleteRq){
        MovieDeleteRqBo movieDeleteRqBo = new MovieDeleteRqBo();

        movieDeleteRqBo.setId(moviesDeleteRq.getId());

        return movieDeleteRqBo;
    }
}
