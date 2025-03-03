package com.ctbcbank.navi.mid.movie.management.service.movies.query;

import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieQueryRq;
import com.ctbcbank.navi.mid.movie.management.controller.movies.payload.MovieQueryRs;

import java.util.List;

public class MoviesQueryConverter {
    public static MovieQueryRqBo parseToBo(MovieQueryRq movieQueryRq){
        MovieQueryRqBo movieQueryRqBo = new MovieQueryRqBo();

        movieQueryRqBo.setId(movieQueryRq.getId());

        return movieQueryRqBo;
    }

    public static MovieQueryRs parseToRs(MovieQueryRsBo movieQueryRsBo){
        MovieQueryRs movieQueryRs = new MovieQueryRs();

        movieQueryRs.setMoviesInfoList(getMoviesInfoList(movieQueryRsBo.getMoviesInfoList()));

        return movieQueryRs;
    }

    private static List<MovieQueryRs.MoviesInfo> getMoviesInfoList(List<MovieQueryRsBo.MoviesInfoBo> moviesInfoBoList){
        List<MovieQueryRs.MoviesInfo> moviesInfoList = moviesInfoBoList.stream().map(x ->{
            MovieQueryRs.MoviesInfo moviesInfo = new MovieQueryRs.MoviesInfo();
            moviesInfo.setMovieName(x.getMovieName());
            moviesInfo.setPrice(x.getPrice());

            return moviesInfo;
        }).toList();

        return moviesInfoList;
    }
}
