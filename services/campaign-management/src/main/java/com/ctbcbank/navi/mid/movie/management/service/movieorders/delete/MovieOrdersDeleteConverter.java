package com.ctbcbank.navi.mid.movie.management.service.movieorders.delete;

import com.ctbcbank.navi.mid.movie.management.controller.movieorders.payload.MovieOrderDeleteRq;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieDeleteRq;
import com.ctbcbank.navi.mid.movie.management.service.movies.delete.MovieDeleteRqBo;

public class MovieOrdersDeleteConverter {
    public static MovieOrderDeleteRqBo parseToBo(MovieOrderDeleteRq movieOrdersDeleteRq){
        MovieOrderDeleteRqBo movieOrderDeleteRqBo = new MovieOrderDeleteRqBo();

        movieOrderDeleteRqBo.setId(movieOrdersDeleteRq.getId());

        return movieOrderDeleteRqBo;
    }
}
