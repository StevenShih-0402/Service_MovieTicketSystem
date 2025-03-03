package com.ctbcbank.navi.mid.movie.management.service.movies.update;

import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieUpdateRq;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieUpdateRs;

public class MoviesUpdateConverter {

    public static MovieUpdateRqBo parseToBo(MovieUpdateRq movieUpdateRq){
        MovieUpdateRqBo movieUpdateRqBo = new MovieUpdateRqBo();

        movieUpdateRqBo.setId(movieUpdateRq.getId());
        movieUpdateRqBo.setMoviesInfo(getMoviesInfoBo(movieUpdateRq.getMoviesInfo()));

        return movieUpdateRqBo;
    }

    private static MovieUpdateRqBo.MoviesInfoBo getMoviesInfoBo(MovieUpdateRq.MoviesInfo moviesInfo){
        MovieUpdateRqBo.MoviesInfoBo moviesInfoBo = new MovieUpdateRqBo.MoviesInfoBo();

        moviesInfoBo.setMovieName(moviesInfo.getMovieName());
        moviesInfoBo.setPrice(moviesInfo.getPrice());

        return moviesInfoBo;
    }

    public static MovieUpdateRs parseToRs(MovieUpdateRsBo movieUpdateRsBo){
        MovieUpdateRs movieUpdateRs = new MovieUpdateRs();

        movieUpdateRs.setMoviesInfo(getMoviesInfo(movieUpdateRsBo.getMoviesInfoBo()));

        return movieUpdateRs;
    }

    private static MovieUpdateRs.MoviesInfo getMoviesInfo(MovieUpdateRsBo.MoviesInfoBo moviesInfoBo){
        MovieUpdateRs.MoviesInfo moviesInfo = new MovieUpdateRs.MoviesInfo();

        moviesInfo.setMovieName(moviesInfoBo.getMovieName());
        moviesInfo.setPrice(moviesInfoBo.getPrice());

        return moviesInfo;
    }
}
